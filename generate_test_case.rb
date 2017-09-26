#!/usr/bin/env ruby
# -*- coding: utf-8 -*-
STDOUT.sync = true

require 'yaml'
require 'FileUtils'
require 'pp'

def writeTestClassHeader() 
    header = <<-GENERATOR
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
    GENERATOR
    header
end
def writeTestClassFooter() 
    footer = <<-GENERATOR
}
    GENERATOR
end

def prepareTestData(testCase)
    routeNames = ["ApproveRouteA","ApproveRouteB", "ApproveRouteC", "ApproveRouteD","ApproveRouteE"]
    prepareDataCodes = []

    for name in routeNames do
        if testCase[name] =~ /need/ then
            case name
            when "ApproveRouteA" then
                prepare = <<-GENERATOR_DOC
            // For Route A insert or update data to the Database 
            dao.update("Update ItemRouteTable ... RouteName = '#{name}' ,itemCategory = '#{testCase["ItemCategory"]}'....");
                GENERATOR_DOC
            when "ApproveRouteB" then
                prepare = <<-GENERATOR_DOC
            // For Route B insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'B' AS RouteType ,'#{testCase["ItemCategory"]}' as ItemCategory....");
            dao.update("Update ItemCategoryTable ... itemCategory = '#{testCase["ItemCategory"]} Where itemShipType = '#{testCase["ItemShipType"]}'")
            dao.update("Update ItemRouteTable ... RouteName = '#{name}' ,itemCategory = '#{testCase["ItemCategory"]}'....");
                GENERATOR_DOC
            when "ApproveRouteC" then
                prepare = <<-GENERATOR_DOC
            // For Route C insert or update data to the Database 
            dao.delete("Delete RouteTable ... where  RouteType == 'C'");
            dao.update("Update ItemRouteTable ... RouteName = '#{name}' ,itemCategory = '#{testCase["ItemCategory"]}'....");
                GENERATOR_DOC
            when "ApproveRouteD" then
                prepare = <<-GENERATOR_DOC
            // For Route D insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'D' AS RouteType ,'#{testCase["ItemCategory"]}' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = '#{name}' ,itemCategory = '#{testCase["ItemCategory"]}'....");
                GENERATOR_DOC
            when "ApproveRouteE" then
                prepare = <<-GENERATOR_DOC
            // For Route E insert or update data to the Database 
            dao.insert("Insert RouteTable ... SELECT 'E' AS RouteType ,'#{testCase["ItemCategory"]}' as ItemCategory....");
            dao.update("Update ItemRouteTable ... RouteName = '#{name}' ,itemCategory = '#{testCase["ItemCategory"]}'....");
                GENERATOR_DOC
            end
            prepareDataCodes.push(prepare);
        end 
    end
    prepareDataCodes
end

def testRouteCall(testCase)
    routeNames = ["ApproveRouteA","ApproveRouteB", "ApproveRouteC", "ApproveRouteD","ApproveRouteE"]
    callCodes = []
    for name in routeNames do
        if testCase[name] =~ /need/ then
            case name
            when "ApproveRouteA" then
                prepare = <<-GENERATOR_DOC
            // approve by A
            route.approveA(item);
                GENERATOR_DOC
            when "ApproveRouteB" then
                prepare = <<-GENERATOR_DOC
            // approve by B
            route.approveB(item);
                GENERATOR_DOC
            when "ApproveRouteC" then
                prepare = <<-GENERATOR_DOC
            // approve by C
            route.approveC(item);
                GENERATOR_DOC
            when "ApproveRouteD" then
                prepare = <<-GENERATOR_DOC
            // approve by D
            route.approveD(item);
                GENERATOR_DOC
            when "ApproveRouteE" then
                prepare = <<-GENERATOR_DOC
            // approve by E
            route.approveE(item);
                GENERATOR_DOC
            end
            callCodes.push(prepare);
        end 
    end
    callCodes
end


def generateTestCase(testCase) 
    outputCodes = []
    # write method header
    outputCodes.push(<<-GENERATOR.gsub(/^$/,"")
        /*
        * <b>Test Case Of [#{testCase["ItemCategory"]}] and [#{testCase["ItemShipType"]}]</b><br>
        * <pre>
        * ======================================================
        * 2017/09/15 Tadayuki Tanigawa
        * </pre>
        * @throws Throwable
        */
        @Test
        public void testCase#{"%02d" % (testCase['caseNo']+ 1)}_#{testCase['ItemCategory'].gsub(/ /,"_")}_#{testCase['ItemShipType'].gsub(/[- ]/,"_")}() throws Throwable {
            // prepare for test data by route
                    GENERATOR
                    );

    # prepare data for each route
    outputCodes.push(prepareTestData(testCase).join())

    # do test methods
    outputCodes.push(<<-GENERATOR

            // create item
            Item item = new Item();
            item.setCategory("#{testCase["ItemCategory"].gsub(/.* /,"")}");
            item.setShipType("#{testCase["ItemShipType"].gsub(/.* /,"")}");

            // call each route 
            Routes routes = new Routes();
            GENERATOR
    )
    outputCodes.push(testRouteCall(testCase).join())

    # assert test result
    outputCodes.push(<<-GENERATOR

            // assert package type
            assertThat("Package Type", routes.packageType, is("#{testCase["PackageType"].gsub(/.* /,"")}"));
            GENERATOR
    )
    outputCodes.push(<<~GENERATOR
            #{testCase["Assertions"]}
            GENERATOR
    )

    outputCodes.push(<<-GENERATOR
        }
            GENERATOR
            )

    outputCodes
end

def main()
    if File.exist?("runnning.tmp") then 
        puts "ruuning prev job"
        return
    end
    File.open("runnning.tmp", 'w') {|f| f.write("test")}
    begin
        yaml = YAML.load(File.open("generated/generator_input.yaml", "r:utf-8").read())

        outputCase = [];
        outputCase.push(writeTestClassHeader());

        testNum = 0
        for i in 0..yaml.length - 1 do
            testCase = yaml[i]
            testCase["caseNo"] = testNum

            outputCase.push(generateTestCase(testCase).join())

            testNum = testNum + 1

        end

        outputCase.push(writeTestClassFooter());

        File.open("./generated/RouteTest.java", "wb:utf-8") do |f|
            outputCase.each { |s| f.write(s.gsub(/[\r\n]/,"\n") + "\n") }
        end
        puts "finished"
    rescue => e
        puts e
        STDERR.puts e.backtrace.join("\n")
        %x( growlnotify.exe -p 2 /t:excel  error!)
        #book.close();
    end
    File.delete("runnning.tmp")
end

main()

