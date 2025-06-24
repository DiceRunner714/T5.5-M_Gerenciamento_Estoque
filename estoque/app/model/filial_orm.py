from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from config.database import Base

class FilialORM(Base):
    __tablename__ = "filial"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String, nullable=False)
    local = Column(String, nullable=False)
    empresa_id = Column(Integer, ForeignKey("empresa.id"), nullable=False)

    empresa = relationship("EmpresaORM", back_populates="filiais")