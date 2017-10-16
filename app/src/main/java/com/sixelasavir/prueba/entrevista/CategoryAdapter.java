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
import android.widget.Toast;

import com.sixelasavir.prueba.entrevista.retrofit.model.category.Children;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by alexis on 12/10/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Children> categories;

    private static final String TYPE_BANNER = "banner";
    private static final String TYPE_ICON = "icon";

    public CategoryAdapter(Context context, List<Children> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Children category = this.categories.get(position);

        Bitmap bitmapBanner = getFile(categories.get(position).getDataChildren().getId().concat("_").concat(TYPE_BANNER));
        Bitmap bitmapIcon = getFile(categories.get(position).getDataChildren().getId().concat("_").concat(TYPE_ICON));


        if (category.getDataChildren().getBannerBitmap() == null)
            if (bitmapBanner == null)
                new ImageTask(category.getDataChildren().getBannerImg(), holder.getBannerImageView(), position, TYPE_BANNER).execute();
            else
                holder.getBannerImageView().setImageBitmap(bitmapBanner);
        else
            holder.getBannerImageView().setImageBitmap(category.getDataChildren().getBannerBitmap());

        if (category.getDataChildren().getIconBitmap() == null)
            if (bitmapIcon == null)
                new ImageTask(category.getDataChildren().getIconImg(), holder.getIconImageView(), position, TYPE_ICON).execute();
            else
                holder.getIconImageView().setImageBitmap(bitmapIcon);
        else
            holder.getIconImageView().setImageBitmap(category.getDataChildren().getIconBitmap());

        holder.getTitleTextView().setText(category.getDataChildren().getTitle());
        holder.getContentTextView().setText(category.getDataChildren().getPublicDescription());
        holder.getReadButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, R.string.msg_info_in_construction, Toast.LENGTH_SHORT).show();
            }
        });
        holder.getGoButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GoListener) context).executeListener(category.getDataChildren().getDisplayNamePrefixed());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView bannerImageView;
        private ImageView iconImageView;
        private TextView titleTextView;
        private TextView contentTextView;
        private Button readButton;
        private Button goButton;


        public ViewHolder(View itemView) {
            super(itemView);

            bannerImageView = (ImageView) itemView.findViewById(R.id.banner);
            iconImageView = (ImageView) itemView.findViewById(R.id.icon);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            contentTextView = (TextView) itemView.findViewById(R.id.content);
            readButton = (Button) itemView.findViewById(R.id.read_btn);
            goButton = (Button) itemView.findViewById(R.id.go_btn);
        }

        public ImageView getBannerImageView() {
            return bannerImageView;
        }

        public ImageView getIconImageView() {
            return iconImageView;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getContentTextView() {
            return contentTextView;
        }

        public Button getReadButton() {
            return readButton;
        }

        public Button getGoButton() {
            return goButton;
        }
    }

    public class ImageTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;
        private int position;
        private String type;

        public ImageTask(String url, ImageView imageView, int position, String type) {
            this.url = url;
            this.imageView = imageView;
            this.position = position;
            this.type = type;
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
                switch (type) {
                    case TYPE_BANNER:
                        categories.get(position).getDataChildren().setBannerBitmap(bitmap);
                        saveFile(categories.get(position).getDataChildren().getId().concat("_").concat(TYPE_BANNER), bitmap);
                        break;
                    case TYPE_ICON:
                        categories.get(position).getDataChildren().setIconBitmap(bitmap);
                        saveFile(categories.get(position).getDataChildren().getId().concat("_").concat(TYPE_ICON), bitmap);
                        break;
                }

                imageView.setImageBitmap(bitmap);
            } else
                imageView.setImageResource(R.mipmap.default_banner_mobile);
        }
    }

    public interface GoListener {
        void executeListener(String path);
    }


    private void saveFile(String dir, Bitmap bitmap) {
        File cacheDir = context.getCacheDir();
        File file = null;
        file = new File(cacheDir, dir.concat(".jpg"));


        try {
            cacheDir.mkdirs();
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Bitmap getFile(String dir) {
        File cacheDir = context.getCacheDir();
        File file = new File(cacheDir, dir.concat(".jpg"));
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            return BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
