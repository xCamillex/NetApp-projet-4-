package com.openclassrooms.netapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.openclassrooms.netapp.Models.GithubUser;
import com.openclassrooms.netapp.Utils.GithubStreams;
import com.openclassrooms.netapp.Utils.ItemClickSupport;
import com.openclassrooms.netapp.Views.GithubUserAdapter;
import com.openclassrooms.netapp.databinding.FragmentMainBinding;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class MainFragment extends Fragment implements GithubUserAdapter.Listener {

    private FragmentMainBinding binding;
    private Disposable disposable;
    private List<GithubUser> githubUsers;
    private GithubUserAdapter adapter;

    public MainFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        configureRecyclerView();
        configureSwipeRefreshLayout();
        configureOnClickRecyclerView();
        executeHttpRequestWithRetrofit();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeWhenDestroy();
    }

    // -----------------
    // ACTION
    // -----------------

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(binding.fragmentMainRecyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    GithubUser user = adapter.getUser(position);
                    Toast.makeText(getContext(), "You clicked on user : " + user.getLogin(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onClickDeleteButton(int position) {
        GithubUser user = adapter.getUser(position);
        Toast.makeText(getContext(), "You are trying to delete user : " + user.getLogin(), Toast.LENGTH_SHORT).show();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView() {
        githubUsers = new ArrayList<>();
        adapter = new GithubUserAdapter(githubUsers, Glide.with(this), this);
        binding.fragmentMainRecyclerView.setAdapter(adapter);
        binding.fragmentMainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureSwipeRefreshLayout() {
        binding.fragmentMainSwipeContainer.setOnRefreshListener(() -> executeHttpRequestWithRetrofit());
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit() {
        disposable = GithubStreams.streamFetchUserFollowing("JakeWharton").subscribeWith(new DisposableObserver<List<GithubUser>>() {
            @Override
            public void onNext(List<GithubUser> users) {
                updateUI(users);
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }
        });
    }

    private void disposeWhenDestroy() {
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<GithubUser> users) {
        githubUsers.clear();
        githubUsers.addAll(users);
        adapter.notifyDataSetChanged();
        binding.fragmentMainSwipeContainer.setRefreshing(false);
    }
}
