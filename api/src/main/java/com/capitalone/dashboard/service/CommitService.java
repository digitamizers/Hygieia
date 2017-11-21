package com.capitalone.dashboard.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.Commit;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.request.CommitRequest;
import com.capitalone.dashboard.request.CommitWithExtraParams;

public interface CommitService {

    /**
     * Finds all of the Commits matching the specified request criteria.
     *
     * @param request search criteria
     * @return commits matching criteria
     */
    DataResponse<Iterable<Commit>> search(CommitRequest request);

    String createFromGitHubv3(JSONObject request) throws ParseException, HygieiaException;

    List<Commit> getCommitsBySha (String scmRevisionNumber);
    
    DataResponse<Iterable<Commit>> searchWithAssetId(CommitWithExtraParams request);
}
