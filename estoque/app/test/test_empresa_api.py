from fastapi.testclient import TestClient

import sys
import os
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

from main import app

client = TestClient(app)

def test_criar_empresa():
    response = client.post("/empresa/", json={"nome": "Empresa Teste"})
    assert response.status_code == 200
    data = response.json()
    assert "id" in data
    assert data["nome"] == "Empresa Teste"

def test_listar_empresas():
    response = client.get("/empresa/")
    assert response.status_code == 200
    data = response.json()
    assert isinstance(data, list)
    assert len(data) > 0