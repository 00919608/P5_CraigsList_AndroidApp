package com.example.listview;

import java.util.Comparator;

/**
 * See builderpattern example project for how to do builders
 * they are essential when constructing complicated objects and
 * with many optional fields
 */
public class BikeData {
    public static final int COMPANY = 0;
    public static final int MODEL = 1;
    public static final int PRICE = 2;
    public static final int LOCATION = 3;
    final String Company;
    final String Model;
    final Double Price;
    final String Location;
    final String Description;
    final String Picture;
    final String Date;
    final String Link;
    //TODO make all BikeData fields final

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO figure out how to print all bikedata out for dialogs
        return "Company:" + this.Company + "\n"+  "Model:" + this.Model +"\n"+  "Price:" + this.Price +"\n"+
        "Location:" + this.Location + "\n" + "Date Listed:"+ this.Date + "\n"+"Description:" + this.Description
                +"\n"+ "Link:" + this.Link;
    }

    private BikeData(Builder b) {
        //TODO
        this.Company = b.Company;
        this.Model = b.Model;
        this.Price = b.Price;
        this.Location = b.Location;
        this.Description = b.Description;
        this.Picture = b.Picture;
        this.Date = b.Date;
        this.Link = b.Link;
    }

    /**
     * @author lynn builder pattern, see page 11 Effective Java UserData mydata
     * = new
     * UserData.Builder(first,last).addProject(proj1).addProject(proj2
     * ).build()
     */
    public static class Builder {
        final String Company;
        final String Model;
        final Double Price;
        String Description;
        String Location;
        String Date;
        String Picture;
        String Link;

        // Model and price required
        Builder(String Company, String Model, Double Price) {
            this.Company = Company;
            this.Model = Model;
            this.Price = Price;
        }

        // the following are setters
        // notice it returns this bulder
        // makes it suitable for chaining
        Builder setDescription(String Description) {
            //TODO manage this
            this.Description = Description;
            return this;
        }

        Builder setLocation(String Location) {
            this.Location = Location;
            return this;
        }

        Builder setDate(String Date) {
            this.Date = Date;
            return this;
        }

        Builder setPicture(String Picture) {
            this.Picture = Picture;
            return this;
        }

        Builder setLink(String Link) {
            this.Link = Link;
            return this;
        }

        // use this to actually construct Bikedata
        // without fear of partial construction
        public BikeData build() {
            return new BikeData(this);
        }
    }

}
class BikeDataCompanyUPComparator implements Comparator<BikeData> {
    public int compare(BikeData myData1, BikeData myData2) {
        return (myData1.Company.compareTo(myData2.Company));
    }
}

class BikeDataPriceUPComparator implements Comparator<BikeData> {
    public int compare(BikeData myData1, BikeData myData2) {
        return (myData1.Price.compareTo(myData2.Price));
    }

}
class BikeDataLocationUPComparator implements Comparator<BikeData> {
    public int compare(BikeData myData1, BikeData myData2) {
        return (myData1.Location.compareTo(myData2.Location));
    }
}

class BikeDataModelUPComparator implements Comparator<BikeData> {
    public int compare(BikeData myData1, BikeData myData2) {
        return (myData1.Model.compareTo(myData2.Model));
    }

}