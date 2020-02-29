package com.time.ttest.module;

import com.time.ttest.TTestApplication;

public interface ApplicationModule<E extends TTestApplication> {

    Object getModule(E application);

}
