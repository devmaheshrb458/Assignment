package com.assignment.network;

import retrofit2.Call;

/**
 *
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 14 Apr, 2018
 */

public interface ApiListener<R> {

    void onSuccess(R response);

    void onFailure(Throwable t);
}
