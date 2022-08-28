package by.makar.boot2.util;


import java.util.Arrays;

public class Counter {
    private int count;
    private Boolean sort;
    private Integer perPage;
    private boolean isNextPageAvailable;
    private boolean isPreviousPageAvailable;

    public Counter()
    {
        count = 0;
    }

    public Counter(int init, Boolean sort, Integer perPage, boolean isNextPageAvailable, boolean isPreviousPageAvailable)
    {
        this.sort = sort;
        count = init;
        this.perPage = perPage;
        this.isNextPageAvailable = isNextPageAvailable;
        this.isPreviousPageAvailable = isPreviousPageAvailable;
    }

    public boolean isNextPageAvailable() {
        return isNextPageAvailable;
    }

    public void setNextPageAvailable(boolean nextPageAvailable) {
        isNextPageAvailable = nextPageAvailable;
    }

    public int get()
    {
        return count;
    }

    public void clear()
    {
        count = 0;
    }

    public int incrementAndGet()
    {
        System.out.println("Preincrement: " + count);
        System.out.println("Increment: " + ++count);
        return count;
    }

    public int decrementAndGet()
    {
        System.out.println("Predecrement: " + count);
        System.out.println("Decrement: " + --count);
        return count;
    }

    public String toString()
    {
        return ""+count;
    }

    public Boolean getSort() {
        return sort;
    }

    public void setSort(Boolean sort) {
        this.sort = sort;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }
    public boolean isPreviousPageAvailable(){
        return  isPreviousPageAvailable;
    }
}
