package com.project.shapelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Shape> shapeList = new ArrayList<Shape>();

    private ListView listView;
    private Button sortButton;
    private Button filterButton;
    private LinearLayout filterView1;
    private LinearLayout filterView2;
    private LinearLayout sortView;

    private Boolean sortHidden = true;
    private Boolean filterHidden = true;

    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSearchWidgets();
        initWidgets();
        setupData();
        setupList();
        setupOnClickListener();
        hideFilter();
        hideSort();

    }

    private void initWidgets() {
        sortButton = (Button) findViewById(R.id.sortButton);
        sortView = (LinearLayout) findViewById(R.id.sortTabsLayout);
        filterButton = (Button) findViewById(R.id.filterButton);
        filterView1 = (LinearLayout) findViewById(R.id.filterTabsLayout);
        filterView2 = (LinearLayout) findViewById(R.id.filterTabsLayout2);
    }

    private void initSearchWidgets() {

        searchView = (SearchView) findViewById(R.id.shapesListSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<Shape> filteredShapes = new ArrayList<Shape>();

                for (Shape shape : shapeList) {
                    if (shape.getName().toLowerCase().contains(s.toLowerCase())) {
                        if (selectedFilter.equals("all")) {
                            filteredShapes.add(shape);
                        } else {
                            if (shape.getName().toLowerCase().contains(selectedFilter)) {
                                filteredShapes.add(shape);
                            }
                        }

                    }
                }
                ShapeAdapter adapter = new ShapeAdapter(getApplicationContext(), 0, filteredShapes);
                listView.setAdapter(adapter);

                return false;
            }
        });

    }

    private void setupData() {
        Shape circle = new Shape("0", "circle", R.drawable.circle);
        shapeList.add(circle);

        Shape triangle = new Shape("1", "triangle", R.drawable.triangle);
        shapeList.add(triangle);

        Shape square = new Shape("2", "square", R.drawable.square);
        shapeList.add(square);

        Shape rectangle = new Shape("3", "rectangle", R.drawable.rectangle);
        shapeList.add(rectangle);

        Shape octagon = new Shape("4", "octagon", R.drawable.octagon);
        shapeList.add(octagon);

        Shape triangle2 = new Shape("5", "Triangle 2", R.drawable.triangle);
        shapeList.add(triangle2);

        Shape square2 = new Shape("6", "Square 2", R.drawable.square);
        shapeList.add(square2);

        Shape rectangle2 = new Shape("7", "Rectangle 2", R.drawable.rectangle);
        shapeList.add(rectangle2);

        Shape octagon2 = new Shape("8", "Octagon 2", R.drawable.octagon);
        shapeList.add(octagon2);
    }

    private void setupList() {
        listView = (ListView) findViewById(R.id.shapesListView);

        ShapeAdapter adapter = new ShapeAdapter(getApplicationContext(), 0, shapeList);
        listView.setAdapter(adapter);
    }

    private void setupOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Shape selectShape = (Shape) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id", selectShape.getId());
                startActivity(showDetail);
            }
        });
    }

    private void filterList(String status) {

        selectedFilter = status;

        ArrayList<Shape> filteredShapes = new ArrayList<Shape>();

        for (Shape shape : shapeList) {
            if (shape.getName().toLowerCase().contains(status)) {
                if (currentSearchText == "") {
                    filteredShapes.add(shape);
                } else {
                    if (shape.getName().toLowerCase().contains(currentSearchText.toLowerCase())) {
                        filteredShapes.add(shape);
                    }
                }
            }
        }
        ShapeAdapter adapter = new ShapeAdapter(getApplicationContext(), 0, filteredShapes);
        listView.setAdapter(adapter);
    }

    public void allFilterTapped(View view) {
        selectedFilter = "all";
        searchView.setQuery("", false);
        searchView.clearFocus();

        ShapeAdapter adapter = new ShapeAdapter(getApplicationContext(), 0, shapeList);
        listView.setAdapter(adapter);
    }

    public void rectangleFilterTapped(View view) {
        filterList("rectangle");
    }

    public void circleFilterTapped(View view) {
        filterList("circle");
    }

    public void squareFilterTapped(View view) {
        filterList("square");
    }

    public void octagonFilterTapped(View view) {
        filterList("octagon");
    }

    public void triangleFilterTapped(View view) {
        filterList("triangle");
    }

    public void showSortTapped(View view) {
        if (sortHidden == true) {
            sortHidden = false;
            showSort();
        } else {
            sortHidden = true;
            hideSort();
        }
    }

    public void showFilterTapped(View view) {
        if (filterHidden == true) {
            filterHidden = false;
            showFilter();
        } else {
            filterHidden = true;
            hideFilter();
        }
    }

    private void hideFilter() {
        searchView.setVisibility(View.GONE);
        filterView1.setVisibility(View.GONE);
        filterView2.setVisibility(View.GONE);
        filterButton.setText("FILTER");
    }

    private void showFilter() {
        searchView.setVisibility(View.VISIBLE);
        filterView1.setVisibility(View.VISIBLE);
        filterView2.setVisibility(View.VISIBLE);
        filterButton.setText("HIDE");
    }

    private void hideSort() {
        sortView.setVisibility(View.GONE);
        sortButton.setText("SORT");
    }

    private void showSort() {
        sortView.setVisibility(View.VISIBLE);
        sortButton.setText("HIDE");
    }

    public void idASCTapped(View view) {
        Collections.sort(shapeList, Shape.idAscending);
        checkForFilter();
    }

    public void idDESCTapped(View view) {
        Collections.sort(shapeList, Shape.idAscending);
        Collections.reverse(shapeList);
        checkForFilter();
    }

    public void nameASCTapped(View view) {
        Collections.sort(shapeList, Shape.nameAscending);
        checkForFilter();
    }

    public void nameDESCTapped(View view) {
        Collections.sort(shapeList, Shape.nameAscending);
        Collections.reverse(shapeList);
        checkForFilter();
    }

    private void checkForFilter() {
        if (selectedFilter.equals("all")) {
            if (currentSearchText.equals("")) {
                setAdapter(shapeList);
            } else {
                ArrayList<Shape> filteredShapes = new ArrayList<Shape>();
                for (Shape shape : shapeList) {
                    if (shape.getName().toLowerCase().contains(currentSearchText)) {
                        filteredShapes.add(shape);
                    }
                }
                setAdapter(filteredShapes);
            }
        } else {
            filterList(selectedFilter);
        }
    }

    private void setAdapter(ArrayList<Shape> shapeList)
    {
        ShapeAdapter adapter = new ShapeAdapter(getApplicationContext(), 0, shapeList);
        listView.setAdapter(adapter);
    }

}