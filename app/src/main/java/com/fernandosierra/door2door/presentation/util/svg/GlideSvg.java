package com.fernandosierra.door2door.presentation.util.svg;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.reactivex.annotations.NonNull;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideSvg {
    private GlideSvg() {
        //Nothing
    }

    public static void loadInto(@NonNull String url, @NonNull ImageView imageView) {
        Glide.with(imageView)
                .as(PictureDrawable.class)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter())
                .load(Uri.parse(url))
                .into(imageView);
    }
}
