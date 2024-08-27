package it.unibo.cloudnine.view.tabs;

import it.unibo.cloudnine.dao.ReceiptsDAO;
import it.unibo.cloudnine.view.View;

import javax.swing.JLabel;

public class EarningsTab extends AbstractTab {

    private float todayEarnings = 0.0f;

    public EarningsTab(final View view) {
        super(view);
        this.add(new JLabel("Today you earned: " + todayEarnings));
    }

    @Override
    void refresh() {
        todayEarnings = ReceiptDAO.getTodaysEarnings();
    }
    
}
