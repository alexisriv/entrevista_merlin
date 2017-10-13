package com.sixelasavir.prueba.entrevista;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixelasavir.prueba.entrevista.retrofit.model.app.Children;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by alexis on 12/10/17.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private Context context;
    private List<Children> apps;

    public AppAdapter(Context context, List<Children> apps) {
        this.context = context;
        this.apps = apps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new AppAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Children app = this.apps.get(position);

        if(app.getDataChildren().getThumbnailBitmap() == null){
            new ImageTask(app.getDataChildren().getThumbnail(), holder.getThumbnailImageView(),position).execute();
        } else {
            holder.getThumbnailImageView().setImageBitmap(app.getDataChildren().getThumbnailBitmap());
        }
        holder.getTitleTextView().setText(app.getDataChildren().getTitle());
        holder.getDetailButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DetailListener) context).executeListener(app.getDataChildren().getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailImageView;
        private TextView titleTextView;
        private Button detailButton;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            detailButton = (Button) itemView.findViewById(R.id.detail_btn);
        }

        public ImageView getThumbnailImageView() {
            return thumbnailImageView;
        }

        public void setThumbnailImageView(ImageView thumbnailImageView) {
            this.thumbnailImageView = thumbnailImageView;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public void setTitleTextView(TextView titleTextView) {
            this.titleTextView = titleTextView;
        }

        public Button getDetailButton() {
            return detailButton;
        }

        public void setDetailButton(Button detailButton) {
            this.detailButton = detailButton;
        }
    }

    public class ImageTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;
        private int position;

        public ImageTask(String url, ImageView imageView, int position) {
            this.url = url;
            this.imageView = imageView;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageView.setImageResource(R.mipmap.default_banner_mobile);
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                Bitmap bitmap;
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                apps.get(position).getDataChildren().setThumbnailBitmap(bitmap);
                imageView.setImageBitmap(bitmap);
            } else
                imageView.setImageResource(R.mipmap.default_banner_mobile);
        }
    }

    public interface DetailListener {
        void executeListener(String url);
    }

}
