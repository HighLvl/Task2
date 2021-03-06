package ru.nsu.cherepanov.task.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;


public class NodeEntity {
    private BigInteger id;
    private double lat;
    private double lon;
    private String userName;
    private BigInteger uid;
    private BigInteger version;
    private BigInteger changeset;
    private Timestamp timestamp;
    private Map<String, String> tags;

    public NodeEntity(BigInteger id, double lat, double lon, String userName, BigInteger uid, BigInteger version, BigInteger changeset, Timestamp timestamp, Map<String, String> tags) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.userName = userName;
        this.uid = uid;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;
        this.tags = tags;
    }

    public NodeEntity() { }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigInteger getUid() {
        return uid;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getChangeset() {
        return changeset;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeEntity that = (NodeEntity) o;
        return Double.compare(that.lat, lat) == 0 && Double.compare(that.lon, lon) == 0 && id.equals(that.id) && userName.equals(that.userName) && uid.equals(that.uid) && version.equals(that.version) && changeset.equals(that.changeset) && timestamp.equals(that.timestamp) && tags.equals(that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lat, lon, userName, uid, version, changeset, timestamp, tags);
    }

    @Override
    public String toString() {
        return "NodeEntity{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", userName='" + userName + '\'' +
                ", uid=" + uid +
                ", version=" + version +
                ", changeset=" + changeset +
                ", timestamp=" + timestamp +
                ", tags=" + tags +
                '}';
    }
}
