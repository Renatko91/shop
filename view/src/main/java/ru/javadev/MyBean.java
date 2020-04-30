package ru.javadev;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class MyBean implements Serializable {
    private boolean showPolicyView = false;

    public void showView() {
        showPolicyView = !showPolicyView;
    }

    public boolean isYourCondition(){
        return showPolicyView;
    }
}
