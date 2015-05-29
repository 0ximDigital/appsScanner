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
public class PostRestService {

    private String url;
    private OkHttpClient client;
    private String json;

    private PostRestServicelistener postRestServicelistener;

    public void setPostRestServicelistener(PostRestServicelistener postRestServicelistener) {
        this.postRestServicelistener = postRestServicelistener;
    }

    public static final MediaType JSON
            = MediaType.parse("Content-Type=application/json; charset=unicode");

    public interface PostRestServicelistener{
        void onResponse(String response);
    }

    public PostRestService(String url, String json) {
        this.url = url;
        this.json = json;
        this.client = new OkHttpClient();
    }

    public void execute() throws ExecutionException, InterruptedException {
        PostWorker postWorker = new PostWorker();
        postWorker.execute();
    }

    private class PostWorker extends AsyncTask<Void, Void, String> {

        private String response;

        @Override
        protected String doInBackground(Void... params) {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST",body)
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
                    this.response = null;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(postRestServicelistener != null){
                postRestServicelistener.onResponse(this.response);
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
