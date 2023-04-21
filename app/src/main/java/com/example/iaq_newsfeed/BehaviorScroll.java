package com.example.iaq_newsfeed;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior;

public class BehaviorScroll extends CoordinatorLayout.Behavior<View> {
  private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
  private static final long ANIMATION_DURATION = 200;

  private View dependencyView;

  private boolean isShowing;
  private boolean isHiding;
  private int dyDirectionSum;

  public BehaviorScroll() {
    super();
  }
  public BehaviorScroll(Context context, AttributeSet attrs) {
    super(context, attrs);
  }


  @Override
  public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
    dependencyView = dependency;
    return dependency instanceof AppBarLayout;
  }
  @Override
  public boolean onRequestChildRectangleOnScreen(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull Rect rectangle, boolean immediate) {
    return super.onRequestChildRectangleOnScreen(parent, child, rectangle, immediate);
  }
  @Override
  public void onDependentViewRemoved(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
    super.onDependentViewRemoved(parent, child, dependency);
  }
  @Override
  public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
    return super.onDependentViewChanged(parent, child, dependency);
  }


  @Override
  public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
  }
  @Override
  public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
    // dy > 0 위로, dy < 0 아래로
    if (dy > 0 && dyDirectionSum < 0 || dy < 0 && dyDirectionSum > 0) {
      child.animate().cancel();
      dyDirectionSum = 0;
    }
    dyDirectionSum += dy;
    if (dyDirectionSum > 0) {
      hideMap(child);
    } else if (dyDirectionSum < 0) {
      showMap(child);
    }
  }

  private void hideMap(View child) {
    if (isHiding || dependencyView.getVisibility() != View.VISIBLE) {
      return;
    }

    ViewPropertyAnimator childAnimator = child.animate()
        .translationY(-600)
        .setInterpolator(INTERPOLATOR)
        .setDuration(ANIMATION_DURATION);
    ViewPropertyAnimator dependencyAnimator = dependencyView.animate()
        .translationY(-600)
        .setInterpolator(INTERPOLATOR)
        .setDuration(ANIMATION_DURATION);

    childAnimator.setListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animator) {
        isHiding = true;
      }

      @Override
      public void onAnimationEnd(Animator animator) {
        isHiding = false;
        dependencyView.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onAnimationCancel(Animator animator) {
        // 취소되면 다시 보여줌
        isHiding = false;
        showMap(child);
      }

      @Override
      public void onAnimationRepeat(Animator animator) {
        // no-op
      }
    });
    childAnimator.start();
    dependencyAnimator.start();
  }
  private void showMap(View child) {
    if (isShowing || dependencyView.getVisibility() == View.VISIBLE) {
      return;
    }
    ViewPropertyAnimator childAnimator = child.animate()
        .translationY(0)
        .setInterpolator(INTERPOLATOR)
        .setDuration(ANIMATION_DURATION);
    ViewPropertyAnimator dependencyAnimator = dependencyView.animate()
        .translationY(0)
        .setInterpolator(INTERPOLATOR)
        .setDuration(ANIMATION_DURATION);

    childAnimator.setListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animator) {
        isShowing = true;
        dependencyView.setVisibility(View.VISIBLE);
      }
      @Override
      public void onAnimationEnd(Animator animator) {
        isShowing = false;
      }
      @Override
      public void onAnimationCancel(Animator animator) {
        // 취소되면 다시 숨김
        isShowing = false;
        hideMap(child);
      }
      @Override
      public void onAnimationRepeat(Animator animator) {
        // no-op
      }
    });
    childAnimator.start();
    dependencyAnimator.start();
  }
}