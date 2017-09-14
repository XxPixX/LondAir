package com.innercirclesoftware.londair.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innercirclesoftware.londair.ui.Message;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements Layoutable, BaseView {

    @Nullable private Unbinder unbinder;
    @Nullable private BasePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detachAllViews();
            presenter.close();
        }
        if (unbinder != null) unbinder.unbind();
        super.onDestroyView();
    }

    protected void registerPresenter(@NonNull BasePresenter presenter) {
        this.presenter = presenter;
        //noinspection unchecked
        presenter.attachView(this);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void showMessage(@NonNull Message message) {
        getBaseActivity().showMessage(message);
    }
}