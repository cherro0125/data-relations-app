package com.kaliszewski.datarelations.data.model.reationship;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NodeConnection {
    private String type;
    private List<String> nodes = new ArrayList<>();
}
