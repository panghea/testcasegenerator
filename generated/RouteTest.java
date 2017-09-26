package jp.price.checker.route;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RouteTest {

        /*
        * <b>Test Case Of [Category A] and [Ship Type B]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase01_Category_A_Ship_Type_B() throws Throwable {
            // prepare for test data by route
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category A' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category A Where itemShipType = 'Ship Type B'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category A'....");
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category A' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category A'....");

            // create item
            Item item = new Item();
            item.setCategory("A");
            item.setShipType("B");

            // call each route 
            Routes routes = new Routes();
            // approve by B
            route.approveB(item);
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("C1"));
            // assert Asset Type of Category A
            assertThat("Asset Type", item.getAssetType(), is("23"));
        }

        /*
        * <b>Test Case Of [Category A] and [Ship Type C]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase02_Category_A_Ship_Type_C() throws Throwable {
            // prepare for test data by route
            // For Route A insert or update data to the Database 
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteA' ,itemCategory = 'Category A'....");
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category A' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category A Where itemShipType = 'Ship Type C'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category A'....");
            // For Route D insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'D' AS RouteType ,'Category A' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteD' ,itemCategory = 'Category A'....");
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category A' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category A'....");

            // create item
            Item item = new Item();
            item.setCategory("A");
            item.setShipType("C");

            // call each route 
            Routes routes = new Routes();
            // approve by A
            route.approveA(item);
            // approve by B
            route.approveB(item);
            // approve by D
            route.approveD(item);
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("C2"));

        }

        /*
        * <b>Test Case Of [Category A] and [Ship Type D]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase03_Category_A_Ship_Type_D() throws Throwable {
            // prepare for test data by route
            // For Route A insert or update data to the Database 
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteA' ,itemCategory = 'Category A'....");
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category A' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category A Where itemShipType = 'Ship Type D'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category A'....");
            // For Route C insert or update data to the Database 
            dao.delete("Delete RouteTable ... where  RouteType == 'C'");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteC' ,itemCategory = 'Category A'....");
            // For Route D insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'D' AS RouteType ,'Category A' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteD' ,itemCategory = 'Category A'....");
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category A' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category A'....");

            // create item
            Item item = new Item();
            item.setCategory("A");
            item.setShipType("D");

            // call each route 
            Routes routes = new Routes();
            // approve by A
            route.approveA(item);
            // approve by B
            route.approveB(item);
            // approve by C
            route.approveC(item);
            // approve by D
            route.approveD(item);
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("A2"));

        }

        /*
        * <b>Test Case Of [Category A] and [Ship Type C]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase04_Category_A_Ship_Type_C() throws Throwable {
            // prepare for test data by route
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category A' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category A'....");

            // create item
            Item item = new Item();
            item.setCategory("A");
            item.setShipType("C");

            // call each route 
            Routes routes = new Routes();
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("A3"));

        }

        /*
        * <b>Test Case Of [Category B] and [Ship Type A]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase05_Category_B_Ship_Type_A() throws Throwable {
            // prepare for test data by route
            // For Route A insert or update data to the Database 
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteA' ,itemCategory = 'Category B'....");
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category B' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category B Where itemShipType = 'Ship Type A'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category B'....");
            // For Route D insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'D' AS RouteType ,'Category B' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteD' ,itemCategory = 'Category B'....");
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category B' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category B'....");

            // create item
            Item item = new Item();
            item.setCategory("B");
            item.setShipType("A");

            // call each route 
            Routes routes = new Routes();
            // approve by A
            route.approveA(item);
            // approve by B
            route.approveB(item);
            // approve by D
            route.approveD(item);
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("C1"));

        }

        /*
        * <b>Test Case Of [Category B] and [Ship Type B]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase06_Category_B_Ship_Type_B() throws Throwable {
            // prepare for test data by route
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category B' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category B Where itemShipType = 'Ship Type B'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category B'....");
            // For Route C insert or update data to the Database 
            dao.delete("Delete RouteTable ... where  RouteType == 'C'");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteC' ,itemCategory = 'Category B'....");
            // For Route D insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'D' AS RouteType ,'Category B' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteD' ,itemCategory = 'Category B'....");
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category B' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category B'....");

            // create item
            Item item = new Item();
            item.setCategory("B");
            item.setShipType("B");

            // call each route 
            Routes routes = new Routes();
            // approve by B
            route.approveB(item);
            // approve by C
            route.approveC(item);
            // approve by D
            route.approveD(item);
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("A2"));

        }

        /*
        * <b>Test Case Of [Category B] and [Ship Type F]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase07_Category_B_Ship_Type_F() throws Throwable {
            // prepare for test data by route
            // For Route A insert or update data to the Database 
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteA' ,itemCategory = 'Category B'....");
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category B' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category B Where itemShipType = 'Ship Type F'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category B'....");
            // For Route D insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'D' AS RouteType ,'Category B' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteD' ,itemCategory = 'Category B'....");
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category B' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category B'....");

            // create item
            Item item = new Item();
            item.setCategory("B");
            item.setShipType("F");

            // call each route 
            Routes routes = new Routes();
            // approve by A
            route.approveA(item);
            // approve by B
            route.approveB(item);
            // approve by D
            route.approveD(item);
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("B7"));

        }

        /*
        * <b>Test Case Of [Category C] and [Ship Type B]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase08_Category_C_Ship_Type_B() throws Throwable {
            // prepare for test data by route
            // For Route A insert or update data to the Database 
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteA' ,itemCategory = 'Category C'....");
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category C' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category C Where itemShipType = 'Ship Type B'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category C'....");
            // For Route C insert or update data to the Database 
            dao.delete("Delete RouteTable ... where  RouteType == 'C'");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteC' ,itemCategory = 'Category C'....");
            // For Route D insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'D' AS RouteType ,'Category C' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteD' ,itemCategory = 'Category C'....");
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category C' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category C'....");

            // create item
            Item item = new Item();
            item.setCategory("C");
            item.setShipType("B");

            // call each route 
            Routes routes = new Routes();
            // approve by A
            route.approveA(item);
            // approve by B
            route.approveB(item);
            // approve by C
            route.approveC(item);
            // approve by D
            route.approveD(item);
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("B4"));

        }

        /*
        * <b>Test Case Of [Category C] and [Ship Type A]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase09_Category_C_Ship_Type_A() throws Throwable {
            // prepare for test data by route
            // For Route A insert or update data to the Database 
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteA' ,itemCategory = 'Category C'....");
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category C' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category C Where itemShipType = 'Ship Type A'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category C'....");
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category C' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category C'....");

            // create item
            Item item = new Item();
            item.setCategory("C");
            item.setShipType("A");

            // call each route 
            Routes routes = new Routes();
            // approve by A
            route.approveA(item);
            // approve by B
            route.approveB(item);
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("A3"));

        }

        /*
        * <b>Test Case Of [Category D] and [Ship Type F]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase10_Category_D_Ship_Type_F() throws Throwable {
            // prepare for test data by route
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category D' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category D Where itemShipType = 'Ship Type F'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category D'....");
            // For Route D insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'D' AS RouteType ,'Category D' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteD' ,itemCategory = 'Category D'....");

            // create item
            Item item = new Item();
            item.setCategory("D");
            item.setShipType("F");

            // call each route 
            Routes routes = new Routes();
            // approve by B
            route.approveB(item);
            // approve by D
            route.approveD(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("C2"));

        }

        /*
        * <b>Test Case Of [Category D] and [Ship Type G]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase11_Category_D_Ship_Type_G() throws Throwable {
            // prepare for test data by route
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'Category D' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = 'Category D Where itemShipType = 'Ship Type G'")
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteB' ,itemCategory = 'Category D'....");
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'Category D' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteE' ,itemCategory = 'Category D'....");

            // create item
            Item item = new Item();
            item.setCategory("D");
            item.setShipType("G");

            // call each route 
            Routes routes = new Routes();
            // approve by B
            route.approveB(item);
            // approve by E
            route.approveE(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("D1"));

        }

        /*
        * <b>Test Case Of [Category E] and [Ship Type A]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase12_Category_E_Ship_Type_A() throws Throwable {
            // prepare for test data by route
            // For Route A insert or update data to the Database 
            dao.update("Update ItemRouteTable ... RouteName = 'ApproveRouteA' ,itemCategory = 'Category E'....");

            // create item
            Item item = new Item();
            item.setCategory("E");
            item.setShipType("A");

            // call each route 
            Routes routes = new Routes();
            // approve by A
            route.approveA(item);

            // assert package type
            assertThat("Package Type", routes.packageType, is("F2"));

        }

}

