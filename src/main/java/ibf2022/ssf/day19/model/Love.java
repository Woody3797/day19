package ibf2022.ssf.day19.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Love implements Comparable<Love>{
    
    private String fname;
    private String sname;
    private String percentage;
    private String result;
    private String id;

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
    public String getPercentage() {
        return percentage;
    }
    public void setPercentage(String percentage) {
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

    public static synchronized String generateId() {
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
            love.setFname(jo.getString("fname"));
            love.setSname(jo.getString("sname"));
            love.setPercentage(jo.getString("percentage"));
            love.setResult(jo.getString("result"));
        }
        return love;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
        .add("fname", fname)
        .add("sname", sname)
        .add("percentage", percentage)
        .add("result", result)
        .build();
    }
    
    @Override
    public int compareTo(Love o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
    

}
