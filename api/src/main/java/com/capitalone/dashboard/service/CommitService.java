package com.capitalone.dashboard.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.Commit;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.request.CommitRequest;
import com.capitalone.dashboard.request.CommitWithAssetIdRequest;

public interface CommitService {

    /**
     * Finds all of the Commits matching the specified request criteria.
     *
     * @param request search criteria
     * @return commits matching criteria
     */
    DataResponse<Iterable<Commit>> search(CommitRequest request);
    /**
     * Finds all of the Commits matching the specified request criteria using assetId
     *
     * @param request search criteria
     * @return commits matching criteria
     */
    DataResponse<Iterable<Commit>> searchWithAssetId(CommitWithAssetIdRequest request);

    String createFromGitHubv3(JSONObject request) throws ParseException, HygieiaException;
}
