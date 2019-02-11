package com.vinod.kafka.springbootkafkaconsumerexample.model;

import java.util.ArrayList;
import java.util.List;

public class ECSRequestMessage {

    private List<Planogram> planograms = new ArrayList<>();

    public void add(Planogram planogram) {
        planograms.add(planogram);
    }

    public void clear() {
        planograms.clear();
    }

    public List<Planogram> getAll() {
        return planograms;
    }
}
