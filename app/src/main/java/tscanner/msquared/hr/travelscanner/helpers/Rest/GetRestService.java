package tscanner.msquared.hr.travelscanner.helpers.Rest;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GetRestService {

    private String url;
    private OkHttpClient client;

    private GetRestServiceListener getRestServiceListener;

    public void setGetRestServiceListener(GetRestServiceListener getRestServiceListener) {
        this.getRestServiceListener = getRestServiceListener;
    }

    public interface GetRestServiceListener{
        void onResponse(String response);
    }

    public GetRestService(String url) {
        this.url = url;
        this.client = new OkHttpClient();
    }

    public void executeRequest() throws ExecutionException, InterruptedException {
        GetWorker getWorker = new GetWorker();
        getWorker.execute();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private class GetWorker extends AsyncTask<Void, Void, Void> {

        private String response;

        @Override
        protected Void doInBackground(Void... params) {

            Request request = new Request.Builder()
                    .url(url)
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
        protected void onPostExecute(Void aVoid) {
            if(getRestServiceListener != null){
                getRestServiceListener.onResponse(this.response);
            }
        }
    }



}

