package com.akondi.business.packaging.mvp.view;

public class MockAddEmployeeView implements AddEmployeeView {
    public boolean submitEnabled;
    public int submitEnabledCount;

    @Override
    public void setSubmitEnabled(boolean value) {
        submitEnabled = value;
        submitEnabledCount++;
    }
}
