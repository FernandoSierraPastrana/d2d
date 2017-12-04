package com.fernandosierra.door2door.presentation.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.airbnb.lottie.LottieAnimationView;

public class LoaderView extends LottieAnimationView {

    public LoaderView(Context context) {
        super(context);
    }

    public LoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void cancelAnimation(@Nullable Runnable onAnimationEnd) {
        if (onAnimationEnd == null) {
            cancelAnimation();
        } else {
            final AnimatorListenerAdapter animatorListener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    removeAnimatorListener(this);
                    onAnimationEnd.run();
                }
            };
            loop(false);
            addAnimatorListener(animatorListener);
        }
    }
}
