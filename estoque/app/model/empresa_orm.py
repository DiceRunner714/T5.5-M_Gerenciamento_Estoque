from sqlalchemy import Column, Integer, String
from config.database import Base

class EmpresaORM(Base):
    __tablename__ = "empresa"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
