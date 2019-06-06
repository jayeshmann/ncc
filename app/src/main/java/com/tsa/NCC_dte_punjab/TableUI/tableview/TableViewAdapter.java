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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.evrencoskun.tableview.sort.SortState;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.TableUI.tableview.holder.CellViewHolder;
import com.tsa.NCC_dte_punjab.TableUI.tableview.holder.ColumnHeaderViewHolder;
import com.tsa.NCC_dte_punjab.TableUI.tableview.holder.RowHeaderViewHolder;
import com.tsa.NCC_dte_punjab.TableUI.tableview.model.Cell;
import com.tsa.NCC_dte_punjab.TableUI.tableview.model.ColumnHeader;
import com.tsa.NCC_dte_punjab.TableUI.tableview.model.RowHeader;

public class TableViewAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    // Cell View Types by Column Position
    private static final int MOOD_CELL_TYPE = 1;
    private static final int GENDER_CELL_TYPE = 2;
    // add new one if it necessary..

    public TableViewAdapter(Context p_jContext) {
        super(p_jContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout;
        // For cells that display a text
        layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_cell_layout,
                parent, false);

        // Create a Cell ViewHolder
        return new CellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int
            columnPosition, int rowPosition) {
        Cell cell = (Cell) cellItemModel;

        CellViewHolder viewHolder = (CellViewHolder) holder;
        viewHolder.setData(cell.getData());
    }

    @Override
    public RecyclerView.ViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Column Header xml Layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout
                .table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object
        columnHeaderItemModel, int columnPosition) {
        ColumnHeader columnHeader = (ColumnHeader) columnHeaderItemModel;

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeader(columnHeader);
    }


    @Override
    public RecyclerView.ViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout
                .table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new RowHeaderViewHolder(layout);
    }


    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel,
                                          int rowPosition) {
        RowHeader rowHeader = (RowHeader) rowHeaderItemModel;

        // Get the holder to update row header item text
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(String.valueOf(rowHeader.getData()));

        // It is necessary to remeasure itself.
        ((RowHeaderViewHolder) holder).itemView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        ((RowHeaderViewHolder) holder).row_header_textview.requestLayout();
    }


    @Override
    public View onCreateCornerView() {
        // Get Corner xml layout
        View corner = LayoutInflater.from(mContext).inflate(R.layout.table_view_corner_layout, null);
        corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortState sortState = TableViewAdapter.this.getTableView().getRowHeaderSortingStatus();
                if (sortState != SortState.ASCENDING) {
                    Log.d("TableViewAdapter", "Order Ascending");
                    TableViewAdapter.this.getTableView().sortRowHeader(SortState.ASCENDING);
                } else {
                    Log.d("TableViewAdapter", "Order Descending");
                    TableViewAdapter.this.getTableView().sortRowHeader(SortState.DESCENDING);
                }
            }
        });
        return corner;
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int column) {
        switch (column) {
            case MainFragment.MOOD_COLUMN_INDEX:
                return MOOD_CELL_TYPE;
            case MainFragment.GENDER_COLUMN_INDEX:
                return GENDER_CELL_TYPE;
            default:
                return 0;
        }
    }
}
