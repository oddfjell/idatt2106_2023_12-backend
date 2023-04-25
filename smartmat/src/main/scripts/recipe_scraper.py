import requests
import csv
import os
from bs4 import BeautifulSoup
from recipe_scrapers import scrape_me
from selenium import webdriver
from selenium.webdriver.firefox.options import Options

options=Options()
options.headless=True
driver = webdriver.Firefox(options=options)
url = "https://www.matprat.no/oppskrifter?filtersActive=23"
driver.get(url)
page = driver.page_source

soup = BeautifulSoup(page, "html.parser")
urls = []
driver.quit()

for a in soup.find_all('a', {'class': 'cm-list-item'}):
    href=a['href']
    urls.append('https://www.matprat.no'+href)

abs_path = os.path.dirname(__file__)
relative_path = "../resources/recipes.csv"
full_path = os.path.join(abs_path, relative_path)

f = open(full_path, 'w')
writer = csv.writer(f)

for url in urls:
    scraper = scrape_me(url)
    csv_line = []
    csv_line.append(url)
    csv_line.append(scraper.title())
    csv_line = csv_line + scraper.ingredients()
    writer.writerow(csv_line)

f.close()
