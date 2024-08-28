package com.openclassrooms.netapp.Views;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.netapp.Models.GithubUser;
import com.openclassrooms.netapp.databinding.FragmentMainItemBinding;

import java.lang.ref.WeakReference;

public class GithubUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final FragmentMainItemBinding binding;
    private WeakReference<GithubUserAdapter.Listener> callbackWeakRef;

    public GithubUserViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = FragmentMainItemBinding.bind(itemView);
        binding.fragmentMainItemDelete.setOnClickListener(this);
    }

    public void updateWithGithubUser(GithubUser githubUser, RequestManager glide, GithubUserAdapter.Listener callback) {
        binding.fragmentMainItemTitle.setText(githubUser.getLogin());
        binding.fragmentMainItemWebsite.setText(githubUser.getHtmlUrl());
        glide.load(githubUser.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.fragmentMainItemImage);
        this.callbackWeakRef = new WeakReference<>(callback);
    }

    @Override
    public void onClick(View view) {
        GithubUserAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) {
            callback.onClickDeleteButton(getAdapterPosition());
        }
    }
}
