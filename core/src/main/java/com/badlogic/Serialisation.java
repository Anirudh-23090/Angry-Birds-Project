package com.badlogic;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.List;

public class Serialisation implements Json.Serializable {
    private List<Data> birds;
    private List<Data> pigs;
    private List<Data> obstacles;
    public Serialisation(List<Data> birds, List<Data> pigs, List<Data> obstacles) {
        this.birds = birds;
        this.pigs = pigs;
        this.obstacles = obstacles;
    }

    public Serialisation() {

    }

    public List<Data> getBirds() {
        return birds;
    }
    public List<Data> getPigs() {
        return pigs;
    }
    public List<Data> getObstacle() {
        return obstacles;
    }

    @Override
    public void write(Json json) {
        json.writeValue("birds", birds);
        json.writeValue("pigs", pigs);
        json.writeValue("obstacles", obstacles);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        birds = json.readValue("birds", List.class, Data.class, jsonValue);
        pigs = json.readValue("pigs", List.class, Data.class, jsonValue);
        obstacles = json.readValue("obstacles", List.class, Data.class, jsonValue);
    }
}
