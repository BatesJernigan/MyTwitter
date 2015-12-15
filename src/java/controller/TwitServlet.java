/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Hashtag;
import business.User;
import dataaccess.TwitRepo;
import business.Twit;
import business.TwitView;
import dataaccess.HashtagRepo;
import dataaccess.TwitHashtagRepo;
import dataaccess.TwitViewRepo;
import dataaccess.UserRepo;
import business.Follow;
import dataaccess.FollowRepo;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Garrick
 */
@WebServlet(name = "TwitServlet", urlPatterns = {"/twit"})
public class TwitServlet extends HttpServlet {
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in do post of twit servlet");
        ArrayList<TwitView> twits = null;
        String queryString = request.getQueryString();
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        String url = "/home.jsp";
        String content = request.getParameter("content");
        
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();

        if (action == null) {
            
            if(queryString != null) {
                System.out.println("search q: " + queryString);
                String parsedQueryString = queryString.split("q=")[1];
                System.out.println("search q: " + parsedQueryString);
                twits = TwitViewRepo.getByHashtagContent(hashtagWrapper(parsedQueryString));
                session.setAttribute("hashtagContent", parsedQueryString);
                System.out.println("twits size: " + twits.size());
                url = "/hashtag.jsp";
            }
        } else if (action.equals("twit")) {
            if(content != null && email != null){
                Twit twit = modifyTwitContent(new Twit(user.getId(), content));
                ArrayList<Hashtag> newHashtags = findNewHashtags(content);
                TwitRepo.addRecord(twit);
                HashtagRepo.insertAll(newHashtags);
                // avoids o(n) queries
                TwitHashtagRepo.insertAll(twit, newHashtags);
            }
        } else if(action.equals("Delete")) {
            System.out.println("action equals DELETE");
            long twitId = Long.parseLong(request.getParameter("twitId"));
            long authorId = Long.parseLong(request.getParameter("userId"));
            System.out.println("twitId: " + twitId + "end");
            if(authorId == user.getId()) {
                TwitRepo.delete(twitId);
            }
        }

        //ArrayList<Follow> notFollowingList = FollowRepo.getNotFollowing(user.getId());
        ArrayList<Follow> followingList = FollowRepo.getFollwing(user.getId());
        twits = TwitViewRepo.all(user, followingList);
        ArrayList<Hashtag> hashtagList = HashtagRepo.getTrending();
        if(hashtagList != null) {
            session.setAttribute("trendingHashtags", hashtagList);
        }
        session.setAttribute("twits", twits);
        session.setAttribute(email, url);

        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in do get of twit servlet");

        HttpSession session = request.getSession();
        ArrayList<Twit> twits = TwitRepo.all();
        session.setAttribute("twits", twits);
        doPost(request, response);
    }
    
    public Twit modifyTwitContent(Twit twit) {
        String adjusted = "";

        for(String wordFromContent: twit.getContent().split(" ")){
            System.out.println("word from content: " + wordFromContent + " end");
            if(wordFromContent.indexOf("#") == 0) {
                String hashtagContent = wordFromContent.substring(1);
                wordFromContent = "<a href=\"/MyTwitter/twit?q=" + hashtagContent +
                    "\" style=\"color:blue\">" + wordFromContent + "</a>";
            }
            if(wordFromContent.indexOf('@') == 0) {
                long userId = UserRepo.selectNickname(wordFromContent.substring(1));
                if(userId != 0) {
                    twit.setMentionedUserId(UserRepo.selectNickname(wordFromContent.substring(1)));
                    wordFromContent = "<span style=\"color:blue\">" + wordFromContent + "</span>";
                    System.out.println("word from content: " + wordFromContent);
                } else {
                    wordFromContent = "";
                }
            }
            adjusted += " " + wordFromContent;
        }

        System.out.println("adjusted stuff");
        System.out.println(adjusted);
        twit.setContent(adjusted);
        return twit;
    }
    
    //FIXME -> O(n) queries if all hashtags already exist due to HashtagRepo.get
    public ArrayList<Hashtag> findNewHashtags(String content) {
        ArrayList<Hashtag> hashtags = new ArrayList<>();
        for(String wordFromContent: content.split(" ")) {
            System.out.println("word from content: " + wordFromContent + " end");
            if(wordFromContent.indexOf("#") == 0) {
                String hashtagContent = wordFromContent.substring(1);
                Hashtag existingHashtag = HashtagRepo.get(hashtagContent);
                System.out.println("existing hashtag from twit repo: " + existingHashtag);
                if(existingHashtag == null) {
                    hashtags.add(new Hashtag(1, hashtagContent));
                } else {
                    Hashtag updatedHashtag = new Hashtag(existingHashtag.getId(),
                            existingHashtag.getCount()+1, hashtagContent);
                    HashtagRepo.update(updatedHashtag);
                    hashtags.add(updatedHashtag);
                }
            }
        }
        return hashtags;
    }
    
    public String hashtagWrapper(String hashtag) {
        return "<a href=\"/MyTwitter/twit?q=" + hashtag +
                    "\" style=\"color:blue\">" + hashtag + "</a>";
    }
}
