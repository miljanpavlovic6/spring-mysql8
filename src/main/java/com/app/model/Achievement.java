package com.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "achievement")
public class Achievement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    @Column(name="achievementid", nullable = false, unique = true)
    private String achievementId;
    @Column(name = "displayname", nullable = false)
    @Length(max = 100)
    private String displayName;
    @Column(name = "description", nullable = false)
    @Length(max = 500)
    private String description;
    @Column(name = "icon")
    private String icon;
    @Column(name = "displayorder")
    private Integer displayOrder;
    @Column(name = "created", nullable = false)
    private Date created;
    @Column(name = "updated", nullable = false)
    private Date updated;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Game game;

    public Achievement() {
    }

    public Achievement(Long id, String achievementId, String displayName, String description, String icon, Integer displayOrder, Date created, Date updated, Game game) {
        this.id = id;
        this.achievementId = achievementId;
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.displayOrder = displayOrder;
        this.created = created;
        this.updated = updated;
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(String achievementId) {
        this.achievementId = achievementId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
