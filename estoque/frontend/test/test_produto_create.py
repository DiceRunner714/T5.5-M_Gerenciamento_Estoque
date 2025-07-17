from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait, Select
from selenium.webdriver.support import expected_conditions as EC
import tempfile
import time
import random

options = Options()
options.add_argument("--headless")
options.add_argument("--no-sandbox")
options.add_argument("--disable-dev-shm-usage")

temp_profile_dir = tempfile.mkdtemp()
options.add_argument(f"--user-data-dir={temp_profile_dir}")

driver = webdriver.Chrome(options=options)
wait = WebDriverWait(driver, 10)

try:
    driver.get("http://localhost:8001/itens/novo/?filial_id=1710")
    time.sleep(1)

    tipo_select = Select(wait.until(EC.presence_of_element_located((By.ID, "tipo"))))
    tipo_select.select_by_value("produto")
    time.sleep(1)

    nome = f"Produto Quimico Create Selenium {random.randint(1000,9999)}"
    driver.find_element(By.NAME, "nome").send_keys(nome)
    driver.find_element(By.NAME, "categoria").send_keys("Químicos Gerais")
    driver.find_element(By.NAME, "valor").clear()
    driver.find_element(By.NAME, "valor").send_keys("7")
    driver.find_element(By.NAME, "quantidade").clear()
    driver.find_element(By.NAME, "quantidade").send_keys("1")
    driver.find_element(By.NAME, "toxicidade").clear()
    driver.find_element(By.NAME, "toxicidade").send_keys("5")

    salvar_btn = wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, "button[type='submit']")))
    driver.execute_script("arguments[0].scrollIntoView(true);", salvar_btn)
    time.sleep(0.5)
    driver.execute_script("arguments[0].click();", salvar_btn)

    time.sleep(3)

    assert nome in driver.page_source
    print(f"\033[92mProduto químico criado com sucesso: {nome}\033[0m")

except Exception as e:
    print("\033[91mErro no teste de criação de produto químico:\033[0m", e)

finally:
    driver.quit()
