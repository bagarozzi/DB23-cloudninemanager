package it.unibo.cloudnine;

import it.unibo.cloudnine.view.View;
import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;

public class CloudNine {

    public static void main(String[] args) {
        FlatMonokaiProIJTheme.setup();
        new View();
    }
}