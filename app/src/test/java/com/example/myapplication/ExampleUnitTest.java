package com.example.myapplication;


import static org.junit.Assert.assertEquals;

import android.app.Application;

import com.example.myapplication.UI.VacationAdapter;
import com.example.myapplication.entities.Vacations;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33)
public class ExampleUnitTest {

    private VacationAdapter adapter;
    private List<Vacations> seed;

    @Before
    public void setUp() {
        Application appContext = RuntimeEnvironment.getApplication();
        adapter = new VacationAdapter(appContext);
        seed = makeSeed();
        adapter.setVacations(seed);
    }

    @Test
    public void filter_emptyQuery_showsAll() {
        adapter.filter("");
        assertEquals(seed.size(), adapter.getItemCount());
    }

    @Test
    public void filter_caseInsensitive_match() {
        adapter.filter("hawaii");
        assertEquals(1, adapter.getItemCount());
    }

    @Test
    public void filter_matchByHotelName() {
        adapter.filter("boutique");
        assertEquals(1, adapter.getItemCount());
    }

    @Test
    public void filter_noMatch_returnsEmpty() {
        adapter.filter("xyz123");
        assertEquals(0, adapter.getItemCount());
    }

    private static List<Vacations> makeSeed() {
        List<Vacations> list = new ArrayList<>();
        list.add(new Vacations(1, "Hawaii", "Great Hotel", "8/21/25", "8/30/25"));
        list.add(new Vacations(2, "SLC", "Less Great Hotel", "8/10/25", "8/20/25"));
        list.add(new Vacations(3, "Paris", "Boutique Hotel", "9/01/25", "9/07/25"));
        return list;
    }
}
