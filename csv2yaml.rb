#!/usr/bin/env ruby
# -*- coding: utf-8 -*-
STDOUT.sync = true

require 'yaml'
require 'FileUtils'
require 'pp'

def main()
    if File.exist?("runnning.tmp") then 
        puts "ruuning prev job"
        return
    end
    File.open("runnning.tmp", 'w') {|f| f.write("test")}
    begin
        begin
            sourceData = YAML.load(File.open("input/data_pattern_csv.yaml", "r:utf-8").read())
        rescue => e
            puts e
            STDERR.puts e.backtrace.join("\n")
            File.delete("runnning.tmp")
            return 
        end

        # colum define same as xlsx column order
        itemColumns = ["ItemCategory" ,"ItemShipType" ,"ApproveRouteA","ApproveRouteB","ApproveRouteC","ApproveRouteD","ApproveRouteE","PackageType"]

        # read whole of data
        itemInfoArray = []
        for row in 0..sourceData["data_csv"].length - 1 do
            itemInfo = {}
            for column in 0..itemColumns.length - 1 do
                itemInfo[itemColumns[column]] = sourceData["data_csv"][row][column]
            end 
            itemInfoArray.push(itemInfo)
        end

        # csv to yaml
        open("./generated/generator_input.yaml","w") do |f|
            YAML.dump(itemInfoArray,f)
        end

        puts "finished"
    rescue => e
        puts e
        STDERR.puts e.backtrace.join("\n")
    end
    File.delete("runnning.tmp")
end

main()
