package com.tsa.NCC_dte_punjab.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.TableUI.tableview.model.Cell;
import com.tsa.NCC_dte_punjab.TableUI.tableview.model.ColumnHeader;
import com.tsa.NCC_dte_punjab.TableUI.tableview.model.RowHeader;

public class CoSummaryAdapter extends AbstractTableAdapter {
    private Context context;
    public CoSummaryAdapter(Context context) {
        super(context);
        this.context=context;
    }

    /**
     * This is sample CellViewHolder class
     * This viewHolder must be extended from AbstractViewHolder class instead of RecyclerView.ViewHolder.
     */
    class MyCellViewHolder extends AbstractViewHolder {

        public final TextView cell_textview;
        //cell_container

        public MyCellViewHolder(View itemView) {
            super(itemView);
            cell_textview = itemView.findViewById(R.id.cell_data);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        // Get cell xml layout
        View layout = LayoutInflater.from(context).inflate(R.layout.table_view_cell_layout,
                parent, false);
        Log.e("cellItemModel","");

        // Create a Custom ViewHolder for a Cell item.
        return new MyCellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int
            columnPosition, int rowPosition) {
        Cell cell = (Cell) cellItemModel;
        // Get the holder to update cell item text
        MyCellViewHolder viewHolder = (MyCellViewHolder) holder;
        viewHolder.cell_textview.setText("jjjjj"+cell.getData());

        // It is necessary to remeasure itself.
       /* viewHolder.itemView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.cell_textview.requestLayout();*/
    }

    class MyColumnHeaderViewHolder extends AbstractViewHolder {

        public final TextView cell_textview;

        public MyColumnHeaderViewHolder(View itemView) {
            super(itemView);
            cell_textview =  itemView.findViewById(R.id.cell_data);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Column Header xml Layout
        View layout = LayoutInflater.from(context).inflate(R.layout
                .table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new MyColumnHeaderViewHolder(layout);
    }


    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int
            position) {
        ColumnHeader columnHeader = (ColumnHeader) columnHeaderItemModel;
        Log.e("columnHeader",columnHeader.toString());
        // Get the holder to update cell item text
        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.cell_textview.setText(""+columnHeader.getData());
    }

    /**
     * This is sample CellViewHolder class.
     * This viewHolder must be extended from AbstractViewHolder class instead of RecyclerView.ViewHolder.
     */
    class MyRowHeaderViewHolder extends AbstractViewHolder {

        //public final TextView cell_textview;
        public TextView row_header_textview;

        public MyRowHeaderViewHolder(View itemView) {
            super(itemView);
            //row_header_container
            //cell_textview =itemView.findViewById(R.id.row_header_textview);
            row_header_textview =itemView.findViewById(R.id.row_header_textview);
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public RecyclerView.ViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(context).inflate(R.layout
                .table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new MyRowHeaderViewHolder(layout);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int
            position) {
        RowHeader rowHeader = (RowHeader) rowHeaderItemModel;

        // Get the holder to update row header item text
        MyRowHeaderViewHolder rowHeaderViewHolder = (MyRowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(""+rowHeader.getData());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public View onCreateCornerView() {
        return LayoutInflater.from(context).inflate(R.layout.table_view_corner_layout, null);
    }

    @Override
    public int getColumnHeaderItemViewType(int columnPosition) {
        return columnPosition;
    }

    @Override
    public int getRowHeaderItemViewType(int rowPosition) {
        return rowPosition;
    }

    @Override
    public int getCellItemViewType(int columnPosition) {
        return columnPosition;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}