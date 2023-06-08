
using Microsoft.AspNetCore.Mvc;

using Stimulsoft.Report;
using Stimulsoft.Report.Angular;
using System.Net.Http.Headers;
using System.Net.Http;
using System.Threading.Tasks;
using System;
using Newtonsoft.Json;
using System.Collections.Generic;

namespace Using_API_Methods.Controllers
{
    [Controller]
    public class ViewerController : Controller
    {
        static ViewerController()
        {
            // How to Activate
            //Stimulsoft.Base.StiLicense.Key = "6vJhGtLLLz2GNviWmUTrhSqnO...";
            //Stimulsoft.Base.StiLicense.LoadFromFile("license.key");
            //Stimulsoft.Base.StiLicense.LoadFromStream(stream);

            Stimulsoft.Base.StiLicense.Key = "6vJhGtLLLz2GNviWmUTrhSqnOItdDwjBylQzQcAOiHmdVlQak8bQq2qNxpmgTY0FXgcdN4b6ZJI7vUKHxLxtKwvhqz" +
                            "oXfmd+TtSYmu3qPVHPVbsbqcjV8vQXucJniGHJ4BtO5lxjJpHzXCSGn/M1fvvWIO2nfiWHoyQBiHlqxYfsNaxBUm6z" +
                            "L5RFc7Xory2cPJwA1r+3aeHEu4VUrWUD9l7ubfPzKPrFnc6H3znf0L1y0Cg/YrIXfc/pt1iZ6W39bQnu9vPZjlbi08" +
                            "+OrMOAK06QC2JdTyMfKeBvX4t/YsYI79O4x1nuBkxHCHdGET+hhNI9+2hfh0C+7QbxSNBYz9rRWQXQKtoeohEKGOPZ" +
                            "2gYj5dNwQa9ybVbNyC3Vtw2t2MB7dVwbaRsogAVfs+SoiUoatptgc9PBsmYdVd5xOoMEU4ieMdXaEOvcbiPZHiMdXc" +
                            "HYAZB02v9QdA/AkN68e6XL6ujqy2KSOs4CMiU2CRGGe/RaVhEH4rjGSyljd2/lheV/XkIAYe6cmwJsBHqb1Bw8jgUe" +
                            "S6kn0iVqYF9ykegQgc5upp1xVCemQxt7nYVcl2dGZLjmThHzzAzmyu3N3Q==";

        }

        [HttpPost]
        public IActionResult InitViewer()
        {


            var requestParams = StiAngularViewer.GetRequestParams(this);

            var options = new StiAngularViewerOptions();
            options.Actions.GetReport = "GetReport";
            options.Actions.ViewerEvent = "ViewerEvent";
            options.Appearance.ScrollbarsMode = true;

            return StiAngularViewer.ViewerDataResult(requestParams, options);
        }

        [HttpPost]
        public IActionResult GetReport()
        {
            var report = StiReport.CreateNewReport();
            //var path = StiAngularHelper.MapPath(this, $"Reports/MasterDetail.mrt");
            //report.Load(path);    

            StiReport reports = addition(report);
            //JsonConvert.DeserializeObject<StiReport>(responseString);
            return StiAngularViewer.GetReportResult(this, reports);
        }

        private string BASE_URL = "http://localhost:8080/";
        public StiReport addition(StiReport stiReport)
        {
            {
                try
                {
                    var client = new HttpClient();
                    client.BaseAddress = new Uri(BASE_URL);
                    client.DefaultRequestHeaders.Accept.Add(
                        new MediaTypeWithQualityHeaderValue("application/json"));
                    HttpResponseMessage response = client.GetAsync("addition").Result;
                    var responseString = response.Content.ReadAsStringAsync().Result;
                    return stiReport.LoadFromString(responseString);
                }
                catch (Exception e)
                {}
                return null;
            }
        }

        [HttpPost]
        public IActionResult ViewerEvent()
        {
            return StiAngularViewer.ViewerEventResult(this);
        }
    }
}


