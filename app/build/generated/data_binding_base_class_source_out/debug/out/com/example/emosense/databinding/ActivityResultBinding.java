// Generated by view binder compiler. Do not edit!
package com.example.emosense.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.emosense.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityResultBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton backButton;

  @NonNull
  public final LinearLayout buttonLayout;

  @NonNull
  public final Button cameraButton;

  @NonNull
  public final CardView headerCardView;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final ImageView previewImageView;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final LinearProgressIndicator progressIndicator;

  @NonNull
  public final ScrollView scrollView2;

  @NonNull
  public final TextView suggestion;

  @NonNull
  public final TextView textView2;

  private ActivityResultBinding(@NonNull ConstraintLayout rootView, @NonNull ImageButton backButton,
      @NonNull LinearLayout buttonLayout, @NonNull Button cameraButton,
      @NonNull CardView headerCardView, @NonNull ConstraintLayout main,
      @NonNull ImageView previewImageView, @NonNull ProgressBar progressBar,
      @NonNull LinearProgressIndicator progressIndicator, @NonNull ScrollView scrollView2,
      @NonNull TextView suggestion, @NonNull TextView textView2) {
    this.rootView = rootView;
    this.backButton = backButton;
    this.buttonLayout = buttonLayout;
    this.cameraButton = cameraButton;
    this.headerCardView = headerCardView;
    this.main = main;
    this.previewImageView = previewImageView;
    this.progressBar = progressBar;
    this.progressIndicator = progressIndicator;
    this.scrollView2 = scrollView2;
    this.suggestion = suggestion;
    this.textView2 = textView2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityResultBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityResultBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_result, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityResultBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backButton;
      ImageButton backButton = ViewBindings.findChildViewById(rootView, id);
      if (backButton == null) {
        break missingId;
      }

      id = R.id.buttonLayout;
      LinearLayout buttonLayout = ViewBindings.findChildViewById(rootView, id);
      if (buttonLayout == null) {
        break missingId;
      }

      id = R.id.cameraButton;
      Button cameraButton = ViewBindings.findChildViewById(rootView, id);
      if (cameraButton == null) {
        break missingId;
      }

      id = R.id.headerCardView;
      CardView headerCardView = ViewBindings.findChildViewById(rootView, id);
      if (headerCardView == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.previewImageView;
      ImageView previewImageView = ViewBindings.findChildViewById(rootView, id);
      if (previewImageView == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.progressIndicator;
      LinearProgressIndicator progressIndicator = ViewBindings.findChildViewById(rootView, id);
      if (progressIndicator == null) {
        break missingId;
      }

      id = R.id.scrollView2;
      ScrollView scrollView2 = ViewBindings.findChildViewById(rootView, id);
      if (scrollView2 == null) {
        break missingId;
      }

      id = R.id.suggestion;
      TextView suggestion = ViewBindings.findChildViewById(rootView, id);
      if (suggestion == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      return new ActivityResultBinding((ConstraintLayout) rootView, backButton, buttonLayout,
          cameraButton, headerCardView, main, previewImageView, progressBar, progressIndicator,
          scrollView2, suggestion, textView2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
