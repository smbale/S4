try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup


with open('README.rst', 'r') as f:
    readme = f.read()

description = ("Ontotext Self-Service Semantic Suite's SDK " +
               "for Python3. For more information, " +
               "visit http://s4.ontotext.com")

setup(
    name="s4api",
    version="1.1.0",
    author="Svetlin Slavov",
    author_email="svetlin.slavov@ontotext.com",

    packages=["s4api", "s4api.models"],
    include_package_data=True,

    url="https://pypi.python.org/pypi/s4api",
    license="Apache License 2.0",
    description=description,

    long_description=readme,

    install_requires=[
        "requests",
    ],
)
