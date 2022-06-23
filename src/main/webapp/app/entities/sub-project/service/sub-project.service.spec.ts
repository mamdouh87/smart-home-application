import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISubProject, SubProject } from '../sub-project.model';

import { SubProjectService } from './sub-project.service';

describe('SubProject Service', () => {
  let service: SubProjectService;
  let httpMock: HttpTestingController;
  let elemDefault: ISubProject;
  let expectedResult: ISubProject | ISubProject[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubProjectService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      projectTemplateCode: 'AAAAAAA',
      projectTemplateNameAr: 'AAAAAAA',
      projectTemplateNameEn: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SubProject', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SubProject()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubProject', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          projectTemplateCode: 'BBBBBB',
          projectTemplateNameAr: 'BBBBBB',
          projectTemplateNameEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubProject', () => {
      const patchObject = Object.assign({}, new SubProject());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubProject', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          projectTemplateCode: 'BBBBBB',
          projectTemplateNameAr: 'BBBBBB',
          projectTemplateNameEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a SubProject', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSubProjectToCollectionIfMissing', () => {
      it('should add a SubProject to an empty array', () => {
        const subProject: ISubProject = { id: 123 };
        expectedResult = service.addSubProjectToCollectionIfMissing([], subProject);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subProject);
      });

      it('should not add a SubProject to an array that contains it', () => {
        const subProject: ISubProject = { id: 123 };
        const subProjectCollection: ISubProject[] = [
          {
            ...subProject,
          },
          { id: 456 },
        ];
        expectedResult = service.addSubProjectToCollectionIfMissing(subProjectCollection, subProject);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubProject to an array that doesn't contain it", () => {
        const subProject: ISubProject = { id: 123 };
        const subProjectCollection: ISubProject[] = [{ id: 456 }];
        expectedResult = service.addSubProjectToCollectionIfMissing(subProjectCollection, subProject);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subProject);
      });

      it('should add only unique SubProject to an array', () => {
        const subProjectArray: ISubProject[] = [{ id: 123 }, { id: 456 }, { id: 25481 }];
        const subProjectCollection: ISubProject[] = [{ id: 123 }];
        expectedResult = service.addSubProjectToCollectionIfMissing(subProjectCollection, ...subProjectArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subProject: ISubProject = { id: 123 };
        const subProject2: ISubProject = { id: 456 };
        expectedResult = service.addSubProjectToCollectionIfMissing([], subProject, subProject2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subProject);
        expect(expectedResult).toContain(subProject2);
      });

      it('should accept null and undefined values', () => {
        const subProject: ISubProject = { id: 123 };
        expectedResult = service.addSubProjectToCollectionIfMissing([], null, subProject, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subProject);
      });

      it('should return initial array if no SubProject is added', () => {
        const subProjectCollection: ISubProject[] = [{ id: 123 }];
        expectedResult = service.addSubProjectToCollectionIfMissing(subProjectCollection, undefined, null);
        expect(expectedResult).toEqual(subProjectCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
