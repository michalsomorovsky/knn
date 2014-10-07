package com.company;

import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;

/**
 * Created by msomorovsky on 07/10/14.
 */
public class ChartDemo extends ApplicationFrame {

    public ChartDemo(final String title, ChartPanel chartPanel){
        super(title);

        setContentPane(chartPanel);
    }
}
