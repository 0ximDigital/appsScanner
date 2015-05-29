package tscanner.msquared.hr.travelscanner.helpers.Rest;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Cveki on 17.12.2014..
 */
public class DeleteRestService {

    private String url;
    private OkHttpClient client;

    public static final MediaType JSON
            = MediaType.parse("Content-Type=application/json; charset=unicode");

    private DeleteRestServiceListener deleteRestServiceListener;

    public void setDeleteRestServiceListener(DeleteRestServiceListener deleteRestServiceListener) {
        this.deleteRestServiceListener = deleteRestServiceListener;
    }

    public interface DeleteRestServiceListener{
        void onResponse(String result);
    }

    public DeleteRestService(String url, String json) {
        this.url = url;
        this.client = new OkHttpClient();
    }

    public void executeRequest() throws ExecutionException, InterruptedException {
        DeleteWorker deleteWorker = new DeleteWorker();
        deleteWorker.execute();
    }

    private class DeleteWorker extends AsyncTask<Void, Void, Void> {
        private String response;

        @Override
        protected Void doInBackground(Void... params) {
            RequestBody body = RequestBody.create(JSON, "");
            Request request = new Request.Builder()
                    .url(url)
                    .method("DELETE",body)
                    .addHeader("Content-Type","application/json")
                    .addHeader("charset","unicode")
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response != null) {
                try {
                    this.response = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(deleteRestServiceListener != null){
                deleteRestServiceListener.onResponse(this.response);
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
