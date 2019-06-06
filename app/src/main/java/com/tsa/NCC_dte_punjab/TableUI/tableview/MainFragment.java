/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.tsa.NCC_dte_punjab.TableUI.tableview;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.filter.Filter;
import com.evrencoskun.tableview.pagination.Pagination;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.TableUI.tableview.model.Cell;
import com.tsa.NCC_dte_punjab.TableUI.tableview.model.ColumnHeader;
import com.tsa.NCC_dte_punjab.TableUI.tableview.model.RowHeader;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;
    private TextView title;

    private AbstractTableAdapter mTableViewAdapter;
    private TableView mTableView;
    private Filter mTableFilter; // This is used for filtering the table.
    private Pagination mPagination; // This is used for paginating the table.

    private MainActivity mainActivity;

    JSONArray insttArray;
    JSONArray JDArray;
    JSONArray JWArray;
    JSONArray SDArray;
    JSONArray SWArray;

    // Columns indexes
    public static final int MOOD_COLUMN_INDEX = 3;
    public static final int GENDER_COLUMN_INDEX = 4;

    private boolean paginationEnabled = false;

    public boolean isPaginationEnabled() {
        return paginationEnabled;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        // initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        title=view.findViewById(R.id.title);
        LinearLayout fragment_container = view.findViewById(R.id.fragment_container);

        // Create Table view
        mTableView = createTableView();
        title.setText("List For Year "+MainActivity.enrollmentYear);
       // mTableView.setSelectedColor(getResources().getColor(R.color.green));

        prinANOList( MainActivity.battalionID,MainActivity.enrollmentYear);

        if (paginationEnabled) {
            mTableFilter = new Filter(mTableView); // Create an instance of a Filter and pass the
            // created TableView.

            // Create an instance for the TableView pagination and pass the created TableView.
            mPagination = new Pagination(mTableView);

            // pagination actions. See onTableViewPageTurnedListener variable declaration below.
            mPagination.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener);
        }
        fragment_container.addView(mTableView);

        // loadData();
        return view;
    }

    private TableView createTableView() {
        TableView tableView = new TableView(getContext());

        // Set adapter
        mTableViewAdapter = new TableViewAdapter(getContext());
        tableView.setAdapter(mTableViewAdapter);

        // Disable shadow
        //tableView.getSelectionHandler().setShadowEnabled(false);

        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);

        // Set TableView listener
        tableView.setTableViewListener(new TableViewListener(tableView));
        return tableView;
    }


    private void initData() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        for (int i = 0; i < insttArray.length()+1; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadData() {
        initData();
        List<RowHeader> rowHeaders = getRowHeaderList();
        List<ColumnHeader> columnHeaders = getColumnHeaderList();
        List<List<Cell>> cellList = getCellListForSortingTest();

        mRowHeaderList.addAll(rowHeaders);
        if (cellList.size() != 0) {
            for (int i = 0; i < cellList.size(); i++) {
                mCellList.get(i).addAll(cellList.get(i));
            }
        } else {
            Toast.makeText(mainActivity, "No Cell Found", Toast.LENGTH_SHORT).show();
        }

        // Load all data
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }

    private List<RowHeader> getRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < insttArray.length()+1; i++) {
            String rh = "" + (i+1);
            RowHeader header = new RowHeader(String.valueOf(i), rh);
            list.add(header);
        }
        return list;
    }


    private List<ColumnHeader> getColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();
        String[] colArr = new String[]{"Institute Name", "JD", "JW", "SD", "SW"};

        for (int i = 0; i < colArr.length; i++) {
            ColumnHeader header = new ColumnHeader(String.valueOf(i), colArr[i]);
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<List<Cell>> getCellListForSortingTest() {

        int TotalSD = 0;
        int TotalSW = 0;
        int TotalJD = 0;
        int TotalJW = 0;

        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < insttArray.length(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Object text = "";
                try {
                    if (j == 0)
                        text = insttArray.getString(i);
                    else if (j == 1) {
                        text = JDArray.getString(i);
                        TotalSD = TotalSD+Integer.parseInt("" + text);
                    } else if (j == 2) {
                        text = JWArray.getString(i);
                        TotalSW = TotalSW+Integer.parseInt("" + text);
                    } else if (j == 3) {
                        text = SDArray.getString(i);
                        TotalJD = TotalJD +Integer.parseInt("" + text);
                    } else if (j == 4) {
                        text = SWArray.getString(i);
                        TotalJW = TotalJW+Integer.parseInt("" + text);
                    } else if (j == 5) {
                        text = Integer.parseInt(JDArray.getString(i)) + Integer.parseInt(JWArray.getString(i)) + Integer.parseInt(SDArray.getString(i)) + Integer.parseInt(SWArray.getString(i));
                        // TotalSD=TotalSD=Integer.parseInt(""+text);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Cell cell;
                cell = new Cell(String.valueOf(i * 10 + j), "" + text);
                cellList.add(cell);
            }
            list.add(cellList);

        }
            List<Cell> cellList = new ArrayList<>();
            cellList.clear();
            cellList.add(new Cell(String.valueOf("Total"), "Total"));
            cellList.add(new Cell(String.valueOf("TotalSD"), "" + TotalSD));
            cellList.add(new Cell(String.valueOf( "TotalSW"), "" + TotalSW));
            cellList.add(new Cell(String.valueOf( "TotalJD"), "" + TotalJD));
            cellList.add(new Cell(String.valueOf( "TotalJW"), "" + TotalJW));
            //cellList.add(new Cell(String.valueOf( "TotalJW"), ""));
            list.add(cellList);

        Log.e("SortingTest", list.toString());

        return list;
    }

    public void filterTable(String filter) {
        mTableFilter.set(filter);
    }

    public void filterTableForMood(String filter) {
        mTableFilter.set(3, filter);
    }

    public void filterTableForGender(String filter) {
        mTableFilter.set(4, filter);
    }

    // The following four methods below: nextTablePage(), previousTablePage(),
    public void nextTablePage() {
        mPagination.nextPage();
    }

    public void previousTablePage() {
        mPagination.previousPage();
    }

    public void goToTablePage(int page) {
        mPagination.goToPage(page);
    }

    public void setTableItemsPerPage(int itemsPerPage) {
        mPagination.setItemsPerPage(itemsPerPage);
    }

    // Handler for the changing of pages in the paginated TableView.
    private Pagination.OnTableViewPageTurnedListener onTableViewPageTurnedListener =
            new Pagination.OnTableViewPageTurnedListener() {
                @Override
                public void onPageTurned(int numItems, int itemsStart, int itemsEnd) {
                    int currentPage = mPagination.getCurrentPage();
                    int pageCount = mPagination.getPageCount();
                    mainActivity.previousButton.setVisibility(View.VISIBLE);
                    mainActivity.nextButton.setVisibility(View.VISIBLE);

                    if (currentPage == 1 && pageCount == 1) {
                        mainActivity.previousButton.setVisibility(View.INVISIBLE);
                        mainActivity.nextButton.setVisibility(View.INVISIBLE);
                    }

                    if (currentPage == 1) {
                        mainActivity.previousButton.setVisibility(View.INVISIBLE);
                    }

                    if (currentPage == pageCount) {
                        mainActivity.nextButton.setVisibility(View.INVISIBLE);
                    }

                    mainActivity.tablePaginationDetails
                            .setText(mainActivity
                                    .getString(
                                            R.string.table_pagination_details,
                                            String.valueOf(currentPage),
                                            String.valueOf(itemsStart),
                                            String.valueOf(itemsEnd)));

                }
            };


    private void prinANOList(final String battalionId,final String year) {

        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "co_instt_wise_summary.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Log.e("HHHH", s.toString());
                        progressDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            Toast.makeText(mainActivity, json.getString("msg"), Toast.LENGTH_SHORT).show();
                            if (status.equals("0")) {

                                insttArray = json.getJSONArray("instt");
                                JDArray = json.getJSONArray("JD");
                                JWArray = json.getJSONArray("JW");
                                SDArray = json.getJSONArray("SD");
                                SWArray = json.getJSONArray("SW");
                            }

                            loadData();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Some issue in loading" + volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("battalion_id", battalionId);
                params.put("enrollment_year", year);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}
