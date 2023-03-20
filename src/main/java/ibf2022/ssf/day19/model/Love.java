package ibf2022.ssf.day19.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.security.SecureRandom;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Love {
    
    private String fname;
    private String sname;
    private Integer percentage;
    private String result;
    private String id;

    public Love(String fname, String sname) {
        this.fname = fname;
        this.sname = sname;
        this.id = generateId();
    }

    public Love() {
        this.id = generateId();
    }

    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getSname() {
        return sname;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public Integer getPercentage() {
        return percentage;
    }
    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public synchronized String generateId() {
        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 8) {
            sb.append(Integer.toHexString(sr.nextInt()));
        }
        return sb.toString().substring(0, 8);
    }

    public static Love create(String json) throws IOException {
        Love love = new Love();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            String person1Name = URLDecoder.decode(jo.getString("fname"), "UTF-8");
            String person2Name = URLDecoder.decode(jo.getString("sname"), "UTF-8");
            love.setFname(person1Name);
            love.setSname(person2Name);
            love.setPercentage(Integer.parseInt(jo.getString("percentage")));
            love.setResult(jo.getString("result"));
        }
        return love;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
        .add("fname", fname)
        .add("sname", sname)
        .add("percentage", percentage.toString())
        .add("result", result)
        .build();
    }

}
