package com.example.newsappusingtab.network_manager;

import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import okhttp3.Headers;

public class NetworkCom<T> {
    final private MutableLiveData<States> state;
    final private MutableLiveData<T> data;
    private Error error;
    private String errorMsg;
    private int httpStatus;
    private Headers responseHeaders;

    private NetworkCom() {
        this.state = new MutableLiveData();
        this.data = new MutableLiveData<>();
    }

    private NetworkCom(MutableLiveData<States> state, MutableLiveData<T> data) {
        this.state = state;
        this.data = data;
    }

    public boolean isLoading() {
        return States.LOADING.equals(state.getValue());
    }

    public void publishSuccess(T data, int httpStatus, Headers responseHeaders) {
        this.error = null;
        this.httpStatus = httpStatus;
        this.responseHeaders = responseHeaders;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.data.setValue(data);
            state.setValue(States.SUCCESS);
        } else {
            this.data.postValue(data);
            state.postValue(States.SUCCESS);
        }
    }

    public void publishError(Error error, String errorMsg, int httpStatus, Headers responseHeaders) {
        this.error = error;
        this.errorMsg = errorMsg;
        this.httpStatus = -1;
        if (Error.HTTP == error) {
            this.httpStatus = httpStatus;
            this.responseHeaders = responseHeaders;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            state.setValue(States.FAILED);
        } else {
            state.postValue(States.FAILED);
        }
    }

    public void startLoading() {
        this.error = null;
        this.errorMsg = null;
        this.httpStatus = -1;
        this.responseHeaders = null;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            state.setValue(States.LOADING);
        } else {
            state.postValue(States.LOADING);
        }
    }

    public LiveData<States> getState() {
        return state;
    }

    public LiveData<T> getData() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public Headers getHeaders() {
        return responseHeaders;
    }

    public enum States {
        LOADING, SUCCESS, FAILED
    }

//    public Map<String, String> getResponseHeaders() {
//        HashMap<String,String> headers= new HashMap<>();
//        if(responseHeaders!=null){
//            headers.putAll(responseHeaders);
//        }
//        return headers;
//    }

    public enum Error {
        HTTP, NETWORK, OTHER
    }

    public static class Factory<T> {
        public NetworkCom<T> createMutableNetworkCom(MutableLiveData<States> state, MutableLiveData<T> data) {
            return new NetworkCom<>(state, data);
        }

        public NetworkCom<T> createNonMutableNetworkCom() {
            return new NetworkCom<>();
        }
    }
}

