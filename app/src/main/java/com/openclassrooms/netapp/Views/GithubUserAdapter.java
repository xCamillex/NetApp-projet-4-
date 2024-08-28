package com.openclassrooms.netapp.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.netapp.Models.GithubUser;
import com.openclassrooms.netapp.databinding.FragmentMainItemBinding;

import java.util.List;

/**
 * Created by Philippe on 22/12/2017.
 */

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserViewHolder> {

    public interface Listener {
        void onClickDeleteButton(int position);
    }

    // FOR COMMUNICATION
    private final Listener callback;

    // FOR DATA
    private List<GithubUser> githubUsers;
    private RequestManager glide;

    // CONSTRUCTOR
    public GithubUserAdapter(List<GithubUser> githubUsers, RequestManager glide, Listener callback) {
        this.githubUsers = githubUsers;
        this.glide = glide;
        this.callback = callback;
    }

    @NonNull
    @Override
    public GithubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        FragmentMainItemBinding binding = FragmentMainItemBinding.inflate(inflater, parent, false);

        return new GithubUserViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUserViewHolder viewHolder, int position) {
        viewHolder.updateWithGithubUser(this.githubUsers.get(position), this.glide, this.callback);
    }

    @Override
    public int getItemCount() {
        return this.githubUsers.size();
    }

    public GithubUser getUser(int position) {
        return this.githubUsers.get(position);
    }
}
