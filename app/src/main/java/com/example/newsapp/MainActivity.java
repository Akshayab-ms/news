package com.example.newsapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jacksonandroidnetworking.JacksonParserFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static String API_KEY="be7ca5a19c2b4823a43a39253c02a8c1";


    private static String TAG="MainActivity";


    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;


    private ArrayList<NewsArticle> mArticleList;

    private ArticleAdapter mArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());


        AndroidNetworking.setParserFactory(new JacksonParserFactory());


        mProgressBar=(ProgressBar)findViewById(R.id.progressbar_id);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycler);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mArticleList=new ArrayList<>();

        get_news_from_api();
    }

    public void get_news_from_api(){

        mArticleList.clear();


        AndroidNetworking.get("https://newsapi.org/v2/top-headlines")
                .addQueryParameter("country", "in")
                .addQueryParameter("apiKey",API_KEY)
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {

                        mProgressBar.setVisibility(View.GONE);

                try {

                            JSONArray articles=response.getJSONArray("articles");

                            for (int j=0;j<articles.length();j++)
                            {  JSONObject article=articles.getJSONObject(j);


                                NewsArticle currentArticle=new NewsArticle();


                                String author=article.getString("author");
                                String title=article.getString("title");
                                String description=article.getString("description");
                                String url=article.getString("url");
                                String urlToImage=article.getString("urlToImage");
                                String publishedAt=article.getString("publishedAt");
                                String content=article.getString("content");

                                currentArticle.setAuthor(author);
                                currentArticle.setTitle(title);
                                currentArticle.setDescription(description);
                                currentArticle.setUrl(url);
                                currentArticle.setUrlToImage(urlToImage);
                                currentArticle.setPublishedAt(publishedAt);
                                currentArticle.setContent(content);


                                mArticleList.add(currentArticle);
                            }

                            mArticleAdapter=new ArticleAdapter(getApplicationContext(),mArticleList);
                            mRecyclerView.setAdapter(mArticleAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.d(TAG,"Error : "+e.getMessage());
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d(TAG,"Error detail : "+error.getErrorDetail());
                        Log.d(TAG,"Error response : "+error.getResponse());
                    }
                });
    }
}
