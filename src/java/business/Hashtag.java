/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Random;

/**
 *
 * @author batesjernigan
 */
public class Hashtag {
    long id, count;
    String content;

    public Hashtag() {}
    
    public Hashtag(long id, long count, String content) {
        this.id = id;
        this.count = count;
        this.content = content;
    }

    public Hashtag(long count, String content) {
        this.id = new Random().nextInt(1000000000);
        this.count = count;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Hashtag{" + "id=" + id + ", count=" + count + ", content=" + content + '}';
    }

}
