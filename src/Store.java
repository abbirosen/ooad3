import java.awt.*;
//import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.Random;

public class Store {

    private ArrayList<Customer> customers;
    private CustomerFactory customerFactory;

    private ArrayList<Car> cars_available;
    private CarFactory carFactory;

    private ArrayList<RentalRecord> rrs = new ArrayList();

    private int totalDayRevenue = 0;

    private ArrayList<Car> currentRentals = new ArrayList();


    private ArrayList<Customer> customerPerDay = new ArrayList();

    private ArrayList<RentalRecord> rrsDaily = new ArrayList();

    private int totalRevenue;
    private String name;




    public Store(String name) { //store constructor
        this.name = name;
        totalRevenue = 0;
        // CUSTOMER
        this.customerFactory = new CustomerFactory(); //instantiate customer factory
        customers = new ArrayList<Customer>();
        // generate 12 customers
        for (int j = 0; j<12; j++){
            Random rand = new Random();
            int rand_int = rand.nextInt(3);
            if (rand_int == 0) { //randomly generate customers
                Customer customer =  customerFactory.getCustomer("BUSINESS", "B"+Integer.toString(j));
                this.customers.add(customer);
//                System.out.println(customer);

            } else if (rand_int == 1){
                Customer customer = customerFactory.getCustomer("CASUAL", "C"+Integer.toString(j));
                this.customers.add(customer);
//                System.out.println(customer);

            } else if (rand_int == 2){
                Customer customer =  customerFactory.getCustomer("REGULAR", "R"+Integer.toString(j));
                this.customers.add(customer);
//                System.out.println(customer);
            }

        }

        //CARS
        cars_available = new ArrayList<Car>();
        this.carFactory = new CarFactory();
        // generate 24 cars
        for (int i = 0; i<24; i++){
            Random rand = new Random();
            int rand_int = rand.nextInt(5); //randomly generate 24 cars of each type
            if (rand_int == 0) { // economy
                BaseCar car =  carFactory.getCar("ECONOMYCAR", i);
                this.cars_available.add(car);
//                System.out.println("license: "+ car.getLicense());
            } else if (rand_int == 1){ // luxury
                BaseCar car =  carFactory.getCar("LUXURYCAR", i);
                this.cars_available.add(car);
//                System.out.println("license: "+ car.getLicense());
            } else if (rand_int == 2){ //minivan
                BaseCar car =  carFactory.getCar("MINIVANCAR", i);
                this.cars_available.add(car);
//                System.out.println("license: "+ car.getLicense());

            } else if (rand_int == 3){ // standard
                BaseCar car =  carFactory.getCar("STANDARDCAR", i);
                this.cars_available.add(car);
//                System.out.println("license: "+ car.getLicense());

            } else { //suv
                BaseCar car =  carFactory.getCar("SUVCAR", i);
                this.cars_available.add(car);
//                System.out.println("license: "+ car.getLicense());
            }
        }

    }

    private Car package_add(Car car) {
        //deal with logic about if the package is added
        Random rand = new Random();

        Car return_with_package = null; //start with null

        int chance = rand.nextInt(10); //generate with random probability add ons and return the packages

        if (chance == 5) { //get a gps
            return_with_package = new GPS(car);
        }

        if (chance%5 == 0) { ///get a car seat
            return_with_package = new CarSeat(car);
        }

        if ((chance == 2)||(chance == 4)) { // get a satellite radio
            return_with_package = new SatelliteRadio(car);
        }
        return return_with_package;


    }
    public int rent(int day) {
        // determine number of customers for a single day
        Random r = new Random();
        int numCustomers = r.nextInt(4);
        System.out.println("Number of customers this day: "+ numCustomers);
        // each customer gets car(s)
        for(int i=0; i<numCustomers;i++){
            // get a random customer
            Random rand2 = new Random();
            int customer_index = rand2.nextInt(this.customers.size());
//
            Customer current_customer = this.customers.get(customer_index);
//

            //determine number of cars the customer wants
            int testNumCars = current_customer.getNumCars();
            if(testNumCars <= cars_available.size()){ //make sure there's enough cars
                // for each car
                for(int j=0; j<testNumCars; j++) { //get the number of cars needed
                    Random rand3 = new Random();
                    int c = rand3.nextInt(cars_available.size());
                    Car current_car = cars_available.get(c);
//
                    this.cars_available.remove(current_car);
                    //                customers.remove(current_customer);
                    System.out.println("Car being rented " + current_customer.getCustName() + " " + testNumCars);

                    //number of nights for customer
                    int currCustNumNights = current_customer.getNumNights();
//
                    //add package
                    Car currentModifiedCar = package_add(current_car); //add a pcakage
                    if(currentModifiedCar == null){
                        currentModifiedCar = current_car; //make sure there's a package added
                    }
//                    else{
//                        System.out.println("add package " +currentModifiedCar.addPackage());
//                        System.out.println("money: " + currentModifiedCar.getRate());
//
//                    }

                    //get total cost for customer
                    System.out.println("add package " +currentModifiedCar.addPackage());
                    int currentRate = currentModifiedCar.getRate();
                    int customerCost = currCustNumNights * currentRate;

//                    System.out.println("customer cost "+customerCost);
                    //System.out.println("COST "+ customerCost);

                    //create rental record
                    RentalRecord rr = new RentalRecord(currentModifiedCar, current_customer, day, customerCost);
                    rr.createRentalRecord();
                    rrs.add(rr);
                    rrsDaily.add(rr);

                    //add to total day revenue
                    this.totalDayRevenue += customerCost;

                    //add to current rentals
                    this.currentRentals.add(currentModifiedCar);
                }


            }



        }
        System.out.println("END OF DAY $"+ this.totalDayRevenue);
        System.out.println("Active Rentals " + rrsDaily.size());
        for(int i=0; i<rrsDaily.size(); i++){
            System.out.println(rrsDaily.get(i).getCustName() + " rented " + rrsDaily.get(i).getCarLicense());
        }
        System.out.println("Cars Left: " + cars_available.size());
        for(int i=0; i<cars_available.size(); i++){
            System.out.println(cars_available.get(i).getLicense());
        }
        this.rrsDaily.clear();
        return this.totalDayRevenue;

    }


    public void returnCars(int day) {
        System.out.println("Completed Rentals:");
        for(int i=0; i<rrs.size(); i++){
            if(rrs.get(i).getReturnDate() == day){
                RentalRecord currRR = rrs.get(i);
                this.cars_available.add(currRR.getCar());
                System.out.println(currRR.getCar().addPackage() + " with LP# "+ currRR.getCarLicense()+ " rented by " + currRR.getCustName() + " for "
                        + currRR.getCustNumNights() + " nights, with a total cost of " + "$"+currRR.getFees());
            }
        }
    }

    public int getTotalRentals(){
        return rrs.size();
    }

    public int getTotalBisRentals(){
        int count = 0;
        for(int i=0; i< rrs.size(); i++){
            if(rrs.get(i).getCustType().equals("Business")){
                count += 1;
            }
        }
        return count;
    }

    public int getTotalCasRentals(){
        int count = 0;
        for(int i=0; i< rrs.size(); i++){
            if(rrs.get(i).getCustType().equals("Casual")){
                count += 1;
            }
        }
        return count;
    }
    public int getTotalRegRentals(){
        int count = 0;
        for(int i=0; i< rrs.size(); i++){
            if(rrs.get(i).getCustType().equals("Regular")){
                count += 1;
            }
        }
        return count;
    }

    public String getName(){
        return this.name;
    }

    public int getCustomersSize(){
        return this.customers.size();
    }

}
