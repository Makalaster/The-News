package com.example.ivonneortega.the_news_project.MainActivity;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class ArticleRefreshService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
