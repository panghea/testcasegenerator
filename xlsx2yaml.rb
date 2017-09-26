#!/usr/bin/env ruby
# -*- coding: utf-8 -*-
STDOUT.sync = true

require 'win32ole'
require 'yaml'
require 'FileUtils'
require 'pp'

# Excel VBA Define
module Excel; end

def init_excel()
    # Create Excel Object
    begin
        excel = WIN32OLE.connect('Excel.Application')
    rescue

    end

    if excel == nil then
        excel = WIN32OLE.new('Excel.Application')
    end
    excel.visible = true
    excel.displayAlerts = false

    WIN32OLE.const_load(excel, Excel)

    return excel
end

def openBook(excel, path) 
    fso = WIN32OLE.new('Scripting.FileSystemObject')
    book = nil
    
    begin
        book = excel.Workbooks[fso.GetFileName(path)]
    rescue
        book = nil
    end
    if book == nil then
        book = excel.Workbooks.Open(fso.GetAbsolutePathName(path))  
    end 
    return book
end 

def getSheet(excel, book, sheet_name) 
    begin
        for i in 1..book.Worksheets.count do
            if( book.Worksheets[i].name.upcase == sheet_name.upcase ) then
                return book.Worksheets[i]
            end
        end  
    rescue
    end
    return nil
end
def main()
    excel  = init_excel()
    if File.exist?("runnning.tmp") then 
        puts "ruuning prev job"
        return
    end
    File.open("runnning.tmp", 'w') {|f| f.write("test")}
    begin
        # open excel and file
        excel = WIN32OLE.connect('Excel.Application')
        excel.ScreenUpdating = false
        excel.DisplayAlerts = false
        book = openBook(excel, File.absolute_path("input/data_pattern.xlsx"))
        sheet = getSheet(excel, book, "ItemShippingPkg")     

        startRow = sheet.Range("DataRange").Row
        startColumn = sheet.Range("DataRange").Column
        startRange = sheet.Rows(startRow).Columns(startColumn)
        # colum define same as xlsx column order
        itemColumns = ["ItemCategory" ,"ItemShipType" ,"ApproveRouteA","ApproveRouteB","ApproveRouteC","ApproveRouteD","ApproveRouteE","PackageType"]

        # read whole of data
        itemInfoArray = []
        for row in 1..sheet.Range("DataRange").Rows.Count - 1 do
            itemInfo = {}
            for column in 0..itemColumns.length - 1 do
                itemInfo[itemColumns[column]] = startRange.offset(row, column).value
            end 
            itemInfoArray.push(itemInfo)
        end

        # xlsx to yaml
        open("./generated/generator_input.yaml","w") do |f|
            YAML.dump(itemInfoArray,f)
        end

        puts "finished"
    rescue => e
        puts e
        STDERR.puts e.backtrace.join("\n")
    end
    excel.ScreenUpdating = true
    File.delete("runnning.tmp")
end

main()
