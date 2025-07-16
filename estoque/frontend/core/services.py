import requests
import ast
from django.conf import settings
from typing import Dict, List, Optional

class APIService:
    def __init__(self):
        self.base_url = settings.FASTAPI_BASE_URL
    
    def _make_request(self, method: str, endpoint: str, data: Dict = None) -> Optional[Dict]:
        """Make HTTP request to FastAPI backend"""
        url = f"{self.base_url}{endpoint}"
        try:
            if method.upper() == 'GET':
                response = requests.get(url)
            elif method.upper() == 'POST':
                response = requests.post(url, json=data)
            elif method.upper() == 'DELETE':
                response = requests.delete(url)
            else:
                return None
            
            if response.status_code in [200, 201]:
                return response.json()
            return None
        except requests.RequestException:
            return None

class EmpresaService(APIService):
    def listar_empresas(self) -> List[Dict]:
        result = self._make_request('GET', '/empresa/')
        return result if result else []
    
    def buscar_empresa(self, empresa_id: int) -> Optional[Dict]:
        return self._make_request('GET', f'/empresa/{empresa_id}')
    
    def criar_empresa(self, data: Dict) -> Optional[Dict]:
        return self._make_request('POST', '/empresa/', data)
    
    def deletar_empresa(self, empresa_id: int) -> bool:
        result = self._make_request('DELETE', f'/empresa/{empresa_id}')
        return result is not None
    
    def estoque_total(self, empresa_id: int) -> Optional[Dict]:
        raw = self._make_request('GET', f'/empresa/{empresa_id}/estoque')
        try:
            if isinstance(raw, str):
                return ast.literal_eval(raw)
            return raw
        except Exception:
            return None

class FilialService(APIService):
    def listar_filiais(self) -> List[Dict]:
        result = self._make_request('GET', '/filial/')
        return result if result else []
    
    def buscar_filial(self, filial_id: int) -> Optional[Dict]:
        return self._make_request('GET', f'/filial/{filial_id}')
    
    def criar_filial(self, data: Dict) -> Optional[Dict]:
        return self._make_request('POST', '/filial/', data)
    
    def deletar_filial(self, filial_id: int) -> bool:
        result = self._make_request('DELETE', f'/filial/{filial_id}')
        return result is not None
    
    def estoque_filial(self, filial_id: int) -> Optional[Dict]:
        return self._make_request('GET', f'/filial/{filial_id}/estoque')
    
    def itens_zerados(self, filial_id: int) -> Optional[Dict]:
        return self._make_request('GET', f'/filial/{filial_id}/itens-zerados')

class ProdutoQuimicoService(APIService):
    def listar_produtos(self) -> List[Dict]:
        result = self._make_request('GET', '/produto_quimico/')
        return result if result else []
    
    def buscar_produto(self, produto_id: int) -> Optional[Dict]:
        return self._make_request('GET', f'/produto_quimico/{produto_id}')
    
    def buscar_por_nome(self, nome: str) -> List[Dict]:
        result = self._make_request('GET', f'/produto_quimico/nome/{nome}')
        return result if result else []
    
    def criar_produto(self, data: Dict) -> Optional[Dict]:
        return self._make_request('POST', '/produto_quimico/', data)
    
    def deletar_produto(self, produto_id: int) -> bool:
        result = self._make_request('DELETE', f'/produto_quimico/{produto_id}')
        return result is not None

class FarmaceuticoService(APIService):
    def listar_farmaceuticos(self) -> List[Dict]:
        result = self._make_request('GET', '/farmaceutico/')
        return result if result else []
    
    def buscar_farmaceutico(self, farmaceutico_id: int) -> Optional[Dict]:
        return self._make_request('GET', f'/farmaceutico/{farmaceutico_id}')
    
    def buscar_por_nome(self, nome: str) -> List[Dict]:
        result = self._make_request('GET', f'/farmaceutico/nome/{nome}')
        return result if result else []
    
    def criar_farmaceutico(self, data: Dict) -> Optional[Dict]:
        return self._make_request('POST', '/farmaceutico/', data)
    
    def deletar_farmaceutico(self, farmaceutico_id: int) -> bool:
        result = self._make_request('DELETE', f'/farmaceutico/{farmaceutico_id}')
        return result is not None