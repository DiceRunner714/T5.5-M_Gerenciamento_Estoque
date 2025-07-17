from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import tempfile
import time

options = Options()
options.add_argument("--headless")
options.add_argument("--no-sandbox")
options.add_argument("--disable-dev-shm-usage")

temp_profile_dir = tempfile.mkdtemp()
options.add_argument(f"--user-data-dir={temp_profile_dir}")

driver = webdriver.Chrome(options=options)
wait = WebDriverWait(driver, 10)

try:
    driver.get("http://localhost:8001/itens/farmaceutico/331/editar/")
    time.sleep(3)

    nome_input = driver.find_element(By.NAME, "nome")
    valor_atual = nome_input.get_attribute("value")

    novo_nome = "Teste Selenium: atualizar farma 1" if valor_atual == "Teste Selenium: atualizar farma 2" else "Teste Selenium: atualizar farma 2"
    nome_input.clear()
    nome_input.send_keys(novo_nome)

    salvar_btn = wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, "button[type='submit']")))
    driver.execute_script("arguments[0].scrollIntoView(true);", salvar_btn)
    time.sleep(0.5)
    driver.execute_script("arguments[0].click();", salvar_btn)

    time.sleep(3)

    assert novo_nome in driver.page_source
    print("\033[92mFarmaceutico atualizado com sucesso\033[0m")

except Exception as e:
    print("\033[91mErro no teste\033[0m", e)

finally:
    driver.quit()
